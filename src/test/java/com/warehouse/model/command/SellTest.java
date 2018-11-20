package com.warehouse.model.command;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.warehouse.WarehouseContextHolder;
import com.warehouse.WarehouseException;
import com.warehouse.model.CommandResponse;
import com.warehouse.model.ProductInfo;
import com.warehouse.model.command.Sell;
import com.warehouse.service.ProductService;

public class SellTest {
	private Sell sell;
	private ProductService productService;
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Before
	public void setUp() {
		productService = EasyMock.createMock(ProductService.class);
		sell = new Sell(productService);
	}
	
	@After
	public void tearDown() {
		EasyMock.reset(productService);
		sell = null;
	}
	
	@Test
	public void testProcessSuccess() {
		String[] input = new String[1];
		input[0] = "4";
		WarehouseContextHolder.setContext(1);
		ProductInfo productInfo = new ProductInfo();
		productInfo.setSlotNumber(1);
		EasyMock.expect(productService.sellProductInSlot(1, 4)).andReturn(true);
		EasyMock.replay(productService);
		CommandResponse response = sell.process(input);
		assertEquals("Slot number 4 is free", response.getMessage());
		EasyMock.verify(productService);
	}
	
	@Test
	public void testProcessFail() {
		String[] input = new String[1];
		input[0] = "4";
		WarehouseContextHolder.setContext(1);
		ProductInfo productInfo = new ProductInfo();
		productInfo.setSlotNumber(1);
		EasyMock.expect(productService.sellProductInSlot(1, 4)).andReturn(false);
		EasyMock.replay(productService);
		CommandResponse response = sell.process(input);
		assertEquals("Nothing to sell in slot number 4", response.getMessage());
		EasyMock.verify(productService);
	}
	
	@Test
	public void testProcessSlotNumberInvalid() {
		String[] input = new String[1];
		input[0] = "a";
		expectedEx.expect(WarehouseException.class);
	    expectedEx.expectMessage("Invalid slot number.");
	    sell.process(input);
	}
	
	@Test
	public void testProcessInputInvalid() {
		String[] input = new String[0];
		expectedEx.expect(WarehouseException.class);
	    expectedEx.expectMessage("Usage: sell <slot number>");
	    sell.process(input);
	}
}
