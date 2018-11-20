package com.warehouse.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.warehouse.model.SlotInfo;

public interface SlotDao extends CrudRepository<SlotInfo, Integer> {
	Optional<SlotInfo> findFirstByWarehouseIdAndOccupiedOrderBySlotNumberAsc(final Integer warehouseId, final Boolean occupied);	
	Optional<SlotInfo> findByWarehouseIdAndSlotNumber(final Integer warehouseId, final Integer slotNumber);
}
