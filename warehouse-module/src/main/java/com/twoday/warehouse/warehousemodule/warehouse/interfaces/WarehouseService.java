package com.twoday.warehouse.warehousemodule.warehouse.interfaces;

import java.util.List;

import com.twoday.dto.dtomodule.ProductDto;
import com.twoday.dto.dtomodule.WarehouseDto;

public interface WarehouseService {

    List<WarehouseDto> getAllWarehouses();
    WarehouseDto saveWarehouse(WarehouseDto warehouseDto);
    ProductDto saveWarehouseProduct(Long id, ProductDto productDto);

}
