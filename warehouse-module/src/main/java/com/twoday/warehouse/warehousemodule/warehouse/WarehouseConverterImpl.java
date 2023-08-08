package com.twoday.warehouse.warehousemodule.warehouse;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.twoday.dto.dtomodule.WarehouseDto;
import com.twoday.warehouse.warehousemodule.product.Product;
import com.twoday.warehouse.warehousemodule.warehouse.interfaces.WarehouseConverter;

@Component
public class WarehouseConverterImpl implements WarehouseConverter {

    @Override
    public WarehouseDto toDto(Warehouse warehouse) {
        WarehouseDto warehouseDto = new WarehouseDto();
        BeanUtils.copyProperties(warehouse, warehouseDto);

        if (warehouse.getProducts() != null) {
            List<Long> productIds = warehouse.getProducts().stream()
                    .map(Product::getId)
                    .toList();
            warehouseDto.setProductIds(productIds);
        }

        return warehouseDto;
    }

    @Override
    public Warehouse fromDto(WarehouseDto warehouseDto) {
        Warehouse warehouse = new Warehouse();
        BeanUtils.copyProperties(warehouseDto, warehouse);
        return warehouse;
    }

}
