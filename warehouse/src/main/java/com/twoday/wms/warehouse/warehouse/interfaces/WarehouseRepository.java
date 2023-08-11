package com.twoday.wms.warehouse.warehouse.interfaces;

import java.util.List;
import java.util.Optional;

import com.twoday.wms.warehouse.interfaces.GenericRepository;
import com.twoday.wms.warehouse.warehouse.Warehouse;

public interface WarehouseRepository extends GenericRepository<Warehouse, Long> {

    List<Warehouse> findAll();
    Warehouse save(Warehouse warehouse);
    Optional<Warehouse> findById(Long id);
    Optional<Warehouse> findByName(String name);
    
}
