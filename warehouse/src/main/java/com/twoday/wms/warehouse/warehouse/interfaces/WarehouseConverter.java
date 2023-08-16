package com.twoday.wms.warehouse.warehouse.interfaces;

import com.twoday.wms.dto.WarehouseDto;
import com.twoday.wms.warehouse.interfaces.GenericConverter;
import com.twoday.wms.warehouse.warehouse.Warehouse;

public interface WarehouseConverter extends GenericConverter<Warehouse, WarehouseDto> {
    
    WarehouseDto toDto(Warehouse warehouse);
    Warehouse fromDto(WarehouseDto warehouseDto);

}
