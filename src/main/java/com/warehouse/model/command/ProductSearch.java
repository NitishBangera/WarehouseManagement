package com.warehouse.model.command;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.warehouse.WarehouseContextHolder;
import com.warehouse.WarehouseException;
import com.warehouse.model.CommandResponse;
import com.warehouse.service.ProductService;

@Component
public class ProductSearch implements Search {
	private Set<String> searchCommands = new HashSet<>();

	private ProductService productService;

	@Autowired
	public ProductSearch(ProductService productService) {
		searchCommands.add("product_codes_for_products_with_colour");
		searchCommands.add("slot_numbers_for_products_with_colour");
		searchCommands.add("slot_number_for_product_code");
		this.productService = productService;
	}

	public Set<String> getSearchCommands() {
		return Collections.unmodifiableSet(searchCommands);
	}

	@Override
	public CommandResponse process(final String[] input) {
		int length = input.length;
		if (length != 2) {
			throw new WarehouseException("Usage: <search command> <search value>");
		}
		
		String searchCommand = input[0];
		String searchValue = input[1];
		if (searchCommands.contains(searchCommand)) {
			Integer warehouseId = WarehouseContextHolder.getContext();
			String searchResult = productService.searchProducts(warehouseId, searchCommand, searchValue);
			return new CommandResponse(searchResult.isEmpty() ? "Not found" : searchResult);
		} else {
			throw new WarehouseException("Invalid search command : " + searchCommand);
		}
	}
}
