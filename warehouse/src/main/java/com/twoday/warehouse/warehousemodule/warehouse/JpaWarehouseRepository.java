package com.twoday.warehouse.warehousemodule.warehouse;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twoday.warehouse.warehousemodule.warehouse.interfaces.WarehouseRepository;

public interface JpaWarehouseRepository extends JpaRepository<Warehouse, Long>, WarehouseRepository {
}
