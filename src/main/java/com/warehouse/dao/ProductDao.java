package com.warehouse.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.warehouse.model.ProductInfo;

public interface ProductDao extends CrudRepository<ProductInfo, Integer> {
	Optional<List<ProductInfo>> findByWarehouseIdOrderBySlotNumberAsc(final Integer warehouseId);
	Optional<List<ProductInfo>> findByWarehouseIdAndColourOrderBySlotNumberAsc(final Integer warehouseId, final String colour);
	Optional<List<ProductInfo>> findByWarehouseIdAndProductCodeOrderBySlotNumberAsc(final Integer warehouseId, final String productCode);
	Optional<ProductInfo> findByWarehouseIdAndSlotNumber(final Integer warehouseId, final Integer slotNumber);
	Integer deleteByWarehouseIdAndSlotNumber(final Integer warehouseId, final Integer slotNumber);
}
