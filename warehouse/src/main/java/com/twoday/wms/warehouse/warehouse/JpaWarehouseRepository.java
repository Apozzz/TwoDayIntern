package com.twoday.wms.warehouse.warehouse;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twoday.wms.warehouse.warehouse.interfaces.WarehouseRepository;

public interface JpaWarehouseRepository extends JpaRepository<Warehouse, Long>, WarehouseRepository {
}
