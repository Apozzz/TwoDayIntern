package com.twoday.wms.warehouse.warehouse.interfaces;

import java.util.List;

import com.twoday.wms.dto.WarehouseDto;

public interface WarehouseService {

    List<WarehouseDto> getAllWarehouses();
    WarehouseDto saveWarehouse(WarehouseDto warehouseDto);

}
