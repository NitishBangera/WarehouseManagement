package com.warehouse.model.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.warehouse.WarehouseContextHolder;
import com.warehouse.WarehouseException;
import com.warehouse.model.CommandResponse;
import com.warehouse.service.ProductService;

@Component
public class Sell implements Command {
	private ProductService productService;
	
	@Autowired
	public Sell(ProductService productService) {
		this.productService = productService;
	}
	
	@Override
	public CommandResponse process(final String[] input) {
		int length = input.length;
		if (length != 1) {
			throw new WarehouseException("Usage: sell <slot number>");
		}
		try {
			int slotNumber = Integer.parseInt(input[0]);
			Integer warehouseId = WarehouseContextHolder.getContext();
			boolean sold = productService.sellProductInSlot(warehouseId, slotNumber);
			if (sold) {
				return new CommandResponse("Slot number " + slotNumber + " is free");
			} else {
				return new CommandResponse("Nothing to sell in slot number " + slotNumber);
			}
		} catch(NumberFormatException nfe) {
			throw new WarehouseException("Invalid slot number.");
		}
	}
}
