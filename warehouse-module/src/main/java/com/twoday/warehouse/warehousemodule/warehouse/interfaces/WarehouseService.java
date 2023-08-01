package com.twoday.warehouse.warehousemodule.warehouse.interfaces;

import java.util.List;

import com.twoday.dto.dtomodule.ProductDto;
import com.twoday.dto.dtomodule.WarehouseDto;

public interface WarehouseService {

    List<WarehouseDto> getAllWarehouses();
    WarehouseDto saveWarehouse(WarehouseDto warehouseDto);
    List<ProductDto> getProductsByWarehouseId(Long id);
    WarehouseDto saveWarehouseProduct(Long id, ProductDto productDto);

}
