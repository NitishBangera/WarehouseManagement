package com.warehouse.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Warehouse")
public class WarehouseInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private int noOfSlots;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNoOfSlots() {
		return noOfSlots;
	}

	public void setNoOfSlots(int noOfSlots) {
		this.noOfSlots = noOfSlots;
	}

	@Override
	public String toString() {
		return "WarehouseInfo [id=" + id + ", noOfSlots=" + noOfSlots + "]";
	}
}
