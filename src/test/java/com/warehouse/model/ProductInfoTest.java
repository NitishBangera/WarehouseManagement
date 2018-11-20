package com.warehouse.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.warehouse.model.ProductInfo;

public class ProductInfoTest {

	@Test
	public void test() {
		ProductInfo productInfo = new ProductInfo();
		productInfo.setId(1);
		productInfo.setProductCode("72527273070");
		productInfo.setSlotNumber(1);
		productInfo.setWarehouseId(2);
		productInfo.setColour("White");
		assertEquals(1, productInfo.getId());
		assertEquals("72527273070", productInfo.getProductCode());
		assertEquals(new Integer(1), productInfo.getSlotNumber());
		assertEquals(2, productInfo.getWarehouseId());
		assertEquals("White", productInfo.getColour());
		assertEquals("ProductInfo [warehouseId=2, slotNumber=1, productCode=72527273070, colour=White]", productInfo.toString());
	}
	
	@Test
	public void testCompare() {
		ProductInfo productInfo1 = new ProductInfo();
		productInfo1.setSlotNumber(1);
		ProductInfo productInfo2 = new ProductInfo();
		productInfo2.setSlotNumber(2);
		assertEquals(-1, productInfo1.compareTo(productInfo2));
		productInfo2.setSlotNumber(1);
		assertEquals(0, productInfo1.compareTo(productInfo2));
		productInfo2.setSlotNumber(0);
		assertEquals(1, productInfo1.compareTo(productInfo2));
	}

}
