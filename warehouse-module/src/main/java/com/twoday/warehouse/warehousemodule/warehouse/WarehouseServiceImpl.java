package com.twoday.warehouse.warehousemodule.warehouse;

import java.util.List;

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
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final ProductConverter productConverter;
    private final WarehouseConverter warehouseConverter;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository, ProductConverter productConverter,
            WarehouseConverter warehouseConverter) {
        this.warehouseRepository = warehouseRepository;
        this.productConverter = productConverter;
        this.warehouseConverter = warehouseConverter;
    }

    @Override
    public List<WarehouseDto> getAllWarehouses() {
        return warehouseRepository.findAll().stream()
                .map(warehouseConverter::toDto)
                .toList();
    }

    @Override
    public WarehouseDto saveWarehouse(WarehouseDto warehouseDto) {
        return warehouseConverter.toDto(warehouseRepository.save(warehouseConverter.fromDto(warehouseDto)));
    }

    @Override
    public ProductDto saveWarehouseProduct(Long id, ProductDto productDto) {
        Product product = productConverter.fromDto(productDto);
        return warehouseRepository.findById(id).map(
                warehouse -> {
                    warehouse.addProduct(product);
                    warehouseRepository.save(warehouse);

                    return productDto;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse with id: %s was not found".formatted(id)));
    }

}
