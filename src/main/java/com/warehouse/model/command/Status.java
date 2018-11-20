package com.warehouse.model.command;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.warehouse.WarehouseContextHolder;
import com.warehouse.WarehouseException;
import com.warehouse.model.CommandResponse;
import com.warehouse.model.ProductInfo;
import com.warehouse.service.ProductService;

@Component
public class Status implements Command {
	private ProductService productService;
	
	@Autowired
	public Status(ProductService productService) {
		this.productService = productService;
	}
	
	@Override
	public CommandResponse process(String[] input) {
		int length = input.length;
		if (length != 0) {
			throw new WarehouseException("Usage: status");
		}
		Integer warehouseId = WarehouseContextHolder.getContext();
		List<ProductInfo> products = productService.getProducts(warehouseId);
		StringBuilder builder = new StringBuilder();
		builder.append("Slot No. Product Code Colour\n");
		for (ProductInfo product : products) {
			builder.append(product.getSlotNumber() + "        " + product.getProductCode() + "  " + product.getColour() + "\n");
		}
		return new CommandResponse(builder.toString());
	}
}
