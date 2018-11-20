package com.warehouse.model.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.warehouse.WarehouseContextHolder;
import com.warehouse.WarehouseException;
import com.warehouse.model.CommandResponse;
import com.warehouse.model.WarehouseInfo;
import com.warehouse.service.WarehouseService;

@Component
public class Warehouse implements Command {
	private static final Logger log = LoggerFactory.getLogger(Warehouse.class);
	private WarehouseService warehouseService;
	
	@Autowired
	public Warehouse(WarehouseService warehouseService) {
		this.warehouseService = warehouseService;
	}
	
	@Override
	public CommandResponse process(final String[] input) {
		int length = input.length;
		if (length < 1 || length > 2) {
			throw new WarehouseException("Usage: warehouse <Number of slots> or warehouse use <warehouse id>");
		}
		try {
			String inputVal = input[0];
			if ("use".equals(inputVal)) {
				int warehouseId = Integer.parseInt(input[1]);
				WarehouseInfo warehouseInfo = warehouseService.getWarehouse(warehouseId);
				if (warehouseInfo != null) {
					WarehouseContextHolder.setContext(warehouseInfo.getId());
					return new CommandResponse("Using warehouse with id " + warehouseInfo.getId());
				} else {
					throw new WarehouseException("No warehouse found for warehouse id : " + warehouseId);
				}
			} else {
				int slots = Integer.parseInt(input[0]);
				WarehouseInfo warehouseInfo = new WarehouseInfo();
				warehouseInfo.setNoOfSlots(slots);
				warehouseInfo = warehouseService.saveWarehouse(warehouseInfo);
				WarehouseContextHolder.setContext(warehouseInfo.getId());
				return new CommandResponse("Created a warehouse with id " + warehouseInfo.getId() + " and " + slots + " slots");
			}
		} catch(Exception ex) {
			log.error("Exception in parsing command", ex);
			throw new WarehouseException("Invalid input value to the command.");
		}
	}
}
