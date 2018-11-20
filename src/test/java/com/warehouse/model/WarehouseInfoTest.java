package com.warehouse.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.warehouse.model.WarehouseInfo;

public class WarehouseInfoTest {
	@Test
	public void test() {
		WarehouseInfo warehouseInfo = new WarehouseInfo();
		warehouseInfo.setId(1);
		warehouseInfo.setNoOfSlots(2);
		assertEquals(2, warehouseInfo.getNoOfSlots());
		assertEquals("WarehouseInfo [id=1, noOfSlots=2]", warehouseInfo.toString());
	}
}
