package com.warehouse.model.command;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.warehouse.WarehouseException;
import com.warehouse.model.CommandResponse;
import com.warehouse.model.WarehouseInfo;
import com.warehouse.model.command.Warehouse;
import com.warehouse.service.WarehouseService;

public class WarehouseTest {
	private Warehouse warehouse;
	private WarehouseService warehouseService;
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Before
	public void setUp() {
		warehouseService = EasyMock.createMock(WarehouseService.class);
		warehouse = new Warehouse(warehouseService);
	}
	
	@After
	public void tearDown() {
		EasyMock.reset(warehouseService);
		warehouse = null;
	}

	@Test
	public void testProcess() {
		String[] input = new String[1];
		input[0] = "6";
		WarehouseInfo warehouseInfo = new WarehouseInfo();
		warehouseInfo.setId(1);
		EasyMock.expect(warehouseService.saveWarehouse(EasyMock.anyObject(WarehouseInfo.class))).andReturn(warehouseInfo);
		EasyMock.replay(warehouseService);
		CommandResponse response = warehouse.process(input);
		assertEquals("Created a warehouse with id 1 and 6 slots", response.getMessage());
		EasyMock.verify(warehouseService);
	}
	
	@Test
	public void testProcessUse() {
		String[] input = new String[2];
		input[0] = "use";
		input[1] = "12";
		WarehouseInfo warehouseInfo = new WarehouseInfo();
		warehouseInfo.setId(1);
		EasyMock.expect(warehouseService.getWarehouse(12)).andReturn(warehouseInfo);
		EasyMock.replay(warehouseService);
		CommandResponse response = warehouse.process(input);
		assertEquals("Using warehouse with id 1", response.getMessage());
		EasyMock.verify(warehouseService);
	}
	
	@Test
	public void testProcessUseWarehouseInvalids() {
		String[] input = new String[2];
		input[0] = "use";
		input[1] = "12";
		expectedEx.expect(WarehouseException.class);
	    expectedEx.expectMessage("Invalid input value to the command.");
		EasyMock.expect(warehouseService.getWarehouse(12)).andReturn(null);
		EasyMock.replay(warehouseService);
		CommandResponse response = warehouse.process(input);
		assertEquals("Using warehouse with id 1", response.getMessage());
		EasyMock.verify(warehouseService);
	}
	
	@Test
	public void testProcessInputInvalid() {
		String[] input = new String[0];
		expectedEx.expect(WarehouseException.class);
	    expectedEx.expectMessage("Usage: warehouse <Number of slots> or warehouse use <warehouse id>");
	    warehouse.process(input);
	}
	
	@Test
	public void testProcessInputParamInvalid() {
		String[] input = new String[1];
		expectedEx.expect(WarehouseException.class);
	    expectedEx.expectMessage("Invalid input value to the command.");
	    warehouse.process(input);
	}
}
