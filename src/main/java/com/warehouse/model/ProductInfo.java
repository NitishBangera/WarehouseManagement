package com.warehouse.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Product")
public class ProductInfo implements Comparable<ProductInfo> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int warehouseId;
	private Integer slotNumber;
	@Column(unique = true)
	private String productCode;
	private String colour;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Integer getSlotNumber() {
		return slotNumber;
	}

	public void setSlotNumber(Integer slotNumber) {
		this.slotNumber = slotNumber;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	@Override
	public int compareTo(ProductInfo o) {
		return this.getSlotNumber().compareTo(o.getSlotNumber());
	}

	@Override
	public String toString() {
		return "ProductInfo [warehouseId=" + warehouseId + ", slotNumber=" + slotNumber + ", productCode=" + productCode
				+ ", colour=" + colour + "]";
	}
}
