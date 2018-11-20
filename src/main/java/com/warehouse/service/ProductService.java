package com.warehouse.service;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.warehouse.dao.ProductDao;
import com.warehouse.dao.SlotDao;
import com.warehouse.model.ProductInfo;
import com.warehouse.model.SlotInfo;

@Service
public class ProductService {
	private static final Logger log = LoggerFactory.getLogger(ProductService.class);

	private ProductDao productDao;
	private SlotDao slotDao;

	@Autowired
	public ProductService(ProductDao productDao, SlotDao slotDao) {
		this.productDao = productDao;
		this.slotDao = slotDao;
	}

	public String searchProducts(final Integer warehouseId, final String searchCommand, final String searchValue) {
		Optional<List<ProductInfo>> products = Optional.empty();
		String returnParam = null;
		switch(searchCommand) {
		case "product_codes_for_products_with_colour":
			products = productDao.findByWarehouseIdAndColourOrderBySlotNumberAsc(warehouseId, searchValue);
			returnParam = "ProductCode";
			break;
		case "slot_numbers_for_products_with_colour":
			products = productDao.findByWarehouseIdAndColourOrderBySlotNumberAsc(warehouseId, searchValue);
			returnParam = "SlotNumber";
			break;
		case "slot_number_for_product_code":
			products = productDao.findByWarehouseIdAndProductCodeOrderBySlotNumberAsc(warehouseId, searchValue);
			returnParam = "SlotNumber";
			break;
		}
		return parseProducts(products, returnParam);
	}

	public List<ProductInfo> getProducts(final Integer warehouseId) {
		Optional<List<ProductInfo>> productResponse = productDao.findByWarehouseIdOrderBySlotNumberAsc(warehouseId);
		if (productResponse.isPresent()) {
			return productResponse.get();
		} else {
			return new LinkedList<ProductInfo>();
		}
	}

	private String parseProducts(Optional<List<ProductInfo>> productResponse, final String returnParam) {
		final StringJoiner joiner = new StringJoiner(",");
		if (productResponse.isPresent()) {
			List<ProductInfo> products = productResponse.get();
			for (ProductInfo product : products) {
				try {
					Method method = product.getClass().getMethod("get" + returnParam);
					Object returnValue = method.invoke(product);
					joiner.add(returnValue.toString());
				} catch (Exception e) {
					log.error("Error:", e);
				}
			}
		}
		return joiner.toString();
	}

	@Transactional
	public boolean sellProductInSlot(final Integer warehouseId, final int slotNumber) {
		boolean sold = false;
		Integer rowsDeleted = productDao.deleteByWarehouseIdAndSlotNumber(warehouseId, slotNumber);
		if (rowsDeleted > 0) {
			Optional<SlotInfo> slotResponse = slotDao.findByWarehouseIdAndSlotNumber(warehouseId, slotNumber);
			SlotInfo slot = slotResponse.get();
			slot.setOccupied(false);
			slotDao.save(slot);
			sold = true;
		} else {
			log.info("No product present in slot number {} of warehouse {}", slotNumber, warehouseId);
		}
		return sold;
	}

	@Transactional
	public ProductInfo saveProduct(ProductInfo productInfo) {
		final int warehouseId = productInfo.getWarehouseId();
		Optional<SlotInfo> slotResponse = slotDao.findFirstByWarehouseIdAndOccupiedOrderBySlotNumberAsc(warehouseId, false);
		if (slotResponse.isPresent()) {
			SlotInfo slotInfo = slotResponse.get();
			productInfo.setSlotNumber(slotInfo.getSlotNumber());
			productDao.save(productInfo);
			slotInfo.setOccupied(true);
			slotDao.save(slotInfo);
		}
		return productInfo;
	}
}
