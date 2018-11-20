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
import com.warehouse.model.command.Store;
import com.warehouse.service.ProductService;

public class StoreTest {
	private Store store;
	private ProductService productService;
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Before
	public void setUp() {
		productService = EasyMock.createMock(ProductService.class);
		store = new Store(productService, 11);
	}
	
	@After
	public void tearDown() {
		EasyMock.reset(productService);
		store = null;
	}

	@Test
	public void testProcess() {
		String[] input = new String[2];
		input[0] = "72527273070";
		input[1] = "White";
		WarehouseContextHolder.setContext(1);
		ProductInfo productInfo = new ProductInfo();
		productInfo.setSlotNumber(1);
		EasyMock.expect(productService.saveProduct(EasyMock.anyObject(ProductInfo.class))).andReturn(productInfo);
		EasyMock.replay(productService);
		CommandResponse response = store.process(input);
		assertEquals("Allocated slot number: 1", response.getMessage());
		EasyMock.verify(productService);
	}
	
	@Test
	public void testProcessWarehouseFull() {
		String[] input = new String[2];
		input[0] = "72527273070";
		input[1] = "White";
		WarehouseContextHolder.setContext(1);
		ProductInfo productInfo = new ProductInfo();
		EasyMock.expect(productService.saveProduct(EasyMock.anyObject(ProductInfo.class))).andReturn(productInfo);
		EasyMock.replay(productService);
		CommandResponse response = store.process(input);
		assertEquals("Warehouse is full", response.getMessage());
		EasyMock.verify(productService);
	}
	
	@Test
	public void testProcessProductCodeInvalid() {
		String[] input = new String[2];
		input[0] = "7252727307";
		input[1] = "White";
		expectedEx.expect(WarehouseException.class);
	    expectedEx.expectMessage("Unique number should be of 11 digits");
		store.process(input);
	}
	
	@Test
	public void testProcessInputInvalid() {
		String[] input = new String[0];
		expectedEx.expect(WarehouseException.class);
	    expectedEx.expectMessage("Usage: store <11-digit Unique Number> <Color>");
		store.process(input);
	}
}
