package com.warehouse.dao;

import org.springframework.data.repository.CrudRepository;

import com.warehouse.model.WarehouseInfo;

public interface WarehouseDao extends CrudRepository<WarehouseInfo, Integer> {
}
