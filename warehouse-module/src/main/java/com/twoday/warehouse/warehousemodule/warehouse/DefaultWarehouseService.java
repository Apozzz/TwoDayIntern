package com.twoday.warehouse.warehousemodule.warehouse;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.twoday.dto.dtomodule.ProductDto;
import com.twoday.dto.dtomodule.WarehouseDto;
import com.twoday.warehouse.warehousemodule.exceptions.ResourceNotFoundException;
import com.twoday.warehouse.warehousemodule.product.Product;
import com.twoday.warehouse.warehousemodule.product.interfaces.ProductConverter;
import com.twoday.warehouse.warehousemodule.warehouse.interfaces.WarehouseConverter;
import com.twoday.warehouse.warehousemodule.warehouse.interfaces.WarehouseRepository;
import com.twoday.warehouse.warehousemodule.warehouse.interfaces.WarehouseService;

@Service
public class DefaultWarehouseService implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final ProductConverter productConverter;
    private final WarehouseConverter warehouseConverter;

    public DefaultWarehouseService(WarehouseRepository warehouseRepository, ProductConverter productConverter,
            WarehouseConverter warehouseConverter) {
        this.warehouseRepository = warehouseRepository;
        this.productConverter = productConverter;
        this.warehouseConverter = warehouseConverter;
    }

    @Override
    public List<WarehouseDto> getAllWarehouses() {
        return warehouseRepository.findAll().stream().map(warehouseConverter::toDto).toList();
    }

    @Override
    public WarehouseDto saveWarehouse(WarehouseDto warehouseDto) {
        return warehouseConverter.toDto(warehouseRepository.save(warehouseConverter.fromDto(warehouseDto)));
    }

    @Override
    public List<ProductDto> getProductsByWarehouseId(Long id) {
        Optional<Warehouse> warehouse = warehouseRepository.findById(id);

        if (warehouse.isPresent()) {
            return warehouse.get().getProducts().stream()
                    .map(productConverter::toDto)
                    .toList();
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public WarehouseDto saveWarehouseProduct(Long id, ProductDto productDto) {
        Product product = productConverter.fromDto(productDto);
        return warehouseRepository.findById(id).map(
                warehouse -> {
                    warehouse.addProduct(product);
                    warehouseRepository.save(warehouse);

                    return warehouseConverter.toDto(warehouse);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse with id: %s was not found".formatted(id)));
    }

}
