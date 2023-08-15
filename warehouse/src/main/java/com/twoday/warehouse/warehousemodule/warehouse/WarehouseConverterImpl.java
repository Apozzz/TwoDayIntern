package com.twoday.warehouse.warehousemodule.warehouse;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.twoday.dto.dtomodule.WarehouseDto;
import com.twoday.warehouse.warehousemodule.product.Product;
import com.twoday.warehouse.warehousemodule.warehouse.interfaces.WarehouseConverter;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WarehouseConverterImpl implements WarehouseConverter {

    private final EntityManager entityManager;

    @Override
    public WarehouseDto toDto(Warehouse warehouse) {
        WarehouseDto warehouseDto = new WarehouseDto();
        BeanUtils.copyProperties(warehouse, warehouseDto);

        List<Long> productIds = warehouse.getProducts().stream()
                .map(Product::getId)
                .toList();
        warehouseDto.setProductIds(productIds);

        return warehouseDto;
    }

    @Override
    public Warehouse fromDto(WarehouseDto warehouseDto) {
        Warehouse warehouse = new Warehouse();
        BeanUtils.copyProperties(warehouseDto, warehouse);

        if (!warehouseDto.getProductIds().isEmpty()) {
            List<Product> productReferences = warehouseDto.getProductIds()
                    .stream()
                    .map(id -> entityManager.getReference(Product.class, id))
                    .collect(Collectors.toList());
            warehouse.setProducts(productReferences);
        }

        return warehouse;
    }

}
