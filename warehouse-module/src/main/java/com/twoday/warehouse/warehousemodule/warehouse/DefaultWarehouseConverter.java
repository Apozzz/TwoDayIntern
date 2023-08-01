package com.twoday.warehouse.warehousemodule.warehouse;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.twoday.dto.dtomodule.WarehouseDto;
import com.twoday.warehouse.warehousemodule.product.Product;
import com.twoday.warehouse.warehousemodule.warehouse.interfaces.WarehouseConverter;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DefaultWarehouseConverter implements WarehouseConverter {

    @Override
    public WarehouseDto toDto(Warehouse warehouse) {
        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setId(warehouse.getId());
        warehouseDto.setName(warehouse.getName());
        warehouseDto.setLocation(warehouse.getLocation());
        warehouseDto.setProductIds(warehouse.getProducts().stream().map(Product::getId).toList());

        return warehouseDto;
    }

    @Override
    public Warehouse fromDto(WarehouseDto warehouseDto) {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseDto.getId());
        warehouse.setName(warehouseDto.getName());
        warehouse.setLocation(warehouseDto.getLocation());
        warehouse.setProducts(new ArrayList<>());

        return warehouse;
    }
    
}
