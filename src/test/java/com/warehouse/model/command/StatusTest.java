package com.warehouse.model.command;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

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
import com.warehouse.model.command.Status;
import com.warehouse.service.ProductService;

public class StatusTest {
	private Status status;
	private ProductService productService;
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Before
	public void setUp() {
		productService = EasyMock.createMock(ProductService.class);
		status = new Status(productService);
	}
	
	@After
	public void tearDown() {
		EasyMock.reset(productService);
		status = null;
	}
	
	@Test
	public void testProcessInputInvalid() {
		String[] input = new String[1];
		expectedEx.expect(WarehouseException.class);
	    expectedEx.expectMessage("Usage: status");
	    status.process(input);
	}
	
	@Test
	public void testProcess() {
		String[] input = new String[0];
		WarehouseContextHolder.setContext(1);
		ProductInfo productInfo = new ProductInfo();
		productInfo.setSlotNumber(1);
		productInfo.setProductCode("72537113173");
		productInfo.setColour("White");
		List<ProductInfo> products = new LinkedList<>();
		products.add(productInfo);
		EasyMock.expect(productService.getProducts(1)).andReturn(products);
		EasyMock.replay(productService);
		CommandResponse response = status.process(input);
		assertEquals("Slot No. Product Code Colour\n1        72537113173  White\n", response.getMessage());
		EasyMock.verify(productService);
	}
}
