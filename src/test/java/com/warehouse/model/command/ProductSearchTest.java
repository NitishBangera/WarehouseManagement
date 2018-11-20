package com.warehouse.model.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.warehouse.WarehouseContextHolder;
import com.warehouse.WarehouseException;
import com.warehouse.model.CommandResponse;
import com.warehouse.model.command.ProductSearch;
import com.warehouse.service.ProductService;

public class ProductSearchTest {
	private ProductSearch productSearch;
	private ProductService productService;
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Before
	public void setUp() {
		productService = EasyMock.createMock(ProductService.class);
		productSearch = new ProductSearch(productService);
	}
	
	@After
	public void tearDown() {
		EasyMock.reset(productService);
		productSearch = null;
	}
	
	@Test
	public void testProcessInputInvalid() {
		String[] input = new String[1];
		expectedEx.expect(WarehouseException.class);
	    expectedEx.expectMessage("Usage: <search command> <search value>");
	    productSearch.process(input);
	}
	
	@Test
	public void testProcessSearchCommandInvalid() {
		String[] input = new String[2];
		input[0] = "test";
		input[1] = "2";
		expectedEx.expect(WarehouseException.class);
	    expectedEx.expectMessage("Invalid search command : test");
	    productSearch.process(input);
	}
	
	@Test
	public void testProcess() {
		String[] input = new String[2];
		input[0] = "product_codes_for_products_with_colour";
		input[1] = "White";
		WarehouseContextHolder.setContext(1);
		EasyMock.expect(productService.searchProducts(1, input[0], input[1])).andReturn("72537113171");
		EasyMock.replay(productService);
		CommandResponse response = productSearch.process(input);
		assertEquals("72537113171", response.getMessage());
		EasyMock.verify(productService);
	}
	
	@Test
	public void testProcessNotFound() {
		String[] input = new String[2];
		input[0] = "product_codes_for_products_with_colour";
		input[1] = "White";
		WarehouseContextHolder.setContext(1);
		EasyMock.expect(productService.searchProducts(1, input[0], input[1])).andReturn("");
		EasyMock.replay(productService);
		CommandResponse response = productSearch.process(input);
		assertEquals("Not found", response.getMessage());
		EasyMock.verify(productService);
	}
	
	@Test
	public void testGetSearchCommands() {
		Set<String> commands = productSearch.getSearchCommands();
		assertTrue(commands.contains("product_codes_for_products_with_colour"));
		assertTrue(commands.contains("slot_numbers_for_products_with_colour"));
		assertTrue(commands.contains("slot_number_for_product_code"));
		assertFalse(commands.contains("slot_number_for_product"));
	}
}
