package com.warehouse.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.warehouse.dao.SlotDao;
import com.warehouse.dao.WarehouseDao;
import com.warehouse.model.SlotInfo;
import com.warehouse.model.WarehouseInfo;

@Service
public class WarehouseService {
	private WarehouseDao warehouseDao;
	private SlotDao slotDao;
	
	@Autowired
	public WarehouseService(WarehouseDao warehouseDao, SlotDao slotDao) {
		this.warehouseDao = warehouseDao;
		this.slotDao = slotDao;
	}

	@Transactional
	public WarehouseInfo saveWarehouse(WarehouseInfo warehouse) {
		WarehouseInfo dbWarehouse = warehouseDao.save(warehouse);
		Integer warehouseId = dbWarehouse.getId();
		List<SlotInfo> productSlots = new LinkedList<SlotInfo>();
		for (int i = 1; i <= warehouse.getNoOfSlots(); i++) {
			SlotInfo slotInfo = new SlotInfo();
			slotInfo.setWarehouseId(warehouseId);
			slotInfo.setSlotNumber(i);
			slotInfo.setOccupied(false);
			productSlots.add(slotInfo);
		}
		slotDao.saveAll(productSlots);
		return dbWarehouse;
	}

	public WarehouseInfo getWarehouse(final int warehouseId) {
		Optional<WarehouseInfo> optionalResponse = warehouseDao.findById(warehouseId);
		return optionalResponse.isPresent() ? optionalResponse.get() : null;
	}
}
