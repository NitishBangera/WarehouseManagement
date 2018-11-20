package com.warehouse.model.command;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.warehouse.WarehouseContextHolder;
import com.warehouse.WarehouseException;
import com.warehouse.model.CommandResponse;
import com.warehouse.model.ProductInfo;
import com.warehouse.service.ProductService;

@Component
public class Store implements Command {
	private int uniqueNumberLength;
	private ProductService productService;
	
	@Autowired
	public Store(ProductService productService, @Value("${application.command.store.product-code-length}") int uniqueNumberLength) {
		this.productService = productService;
		this.uniqueNumberLength = uniqueNumberLength;
	}
	
	@Override
	public CommandResponse process(String[] input) {
		int length = input.length;
		if (length != 2) {
			throw new WarehouseException("Usage: store <"+ uniqueNumberLength + "-digit Unique Number> <Color>");
		}

		Pattern digitPattern = Pattern.compile("\\d{"+ uniqueNumberLength +"}");
		String uniqueNumber = input[0];
		if (!digitPattern.matcher(uniqueNumber).matches()) {
			throw new WarehouseException("Unique number should be of " + uniqueNumberLength + " digits");
		}
		String color = input[1];
		Integer warehouseId = WarehouseContextHolder.getContext();
		ProductInfo productInfo = new ProductInfo();
		productInfo.setProductCode(uniqueNumber);
		productInfo.setColour(color);
		productInfo.setWarehouseId(warehouseId);
		productInfo = productService.saveProduct(productInfo);
		Integer allocatedSlot = productInfo.getSlotNumber();
		if (allocatedSlot == null) {
			return new CommandResponse("Warehouse is full");
		}
		return new CommandResponse("Allocated slot number: " + allocatedSlot);
	}
}
