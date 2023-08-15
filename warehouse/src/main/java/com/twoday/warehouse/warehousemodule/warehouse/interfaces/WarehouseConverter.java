package com.twoday.warehouse.warehousemodule.warehouse.interfaces;

import com.twoday.dto.dtomodule.WarehouseDto;
import com.twoday.warehouse.warehousemodule.interfaces.GenericConverter;
import com.twoday.warehouse.warehousemodule.warehouse.Warehouse;

public interface WarehouseConverter extends GenericConverter<Warehouse, WarehouseDto> {
    
    WarehouseDto toDto(Warehouse warehouse);
    Warehouse fromDto(WarehouseDto WarehouseDto);

}
