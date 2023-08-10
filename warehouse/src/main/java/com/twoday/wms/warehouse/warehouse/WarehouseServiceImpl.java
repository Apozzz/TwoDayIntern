package com.twoday.wms.warehouse.warehouse;

import java.util.List;

import org.springframework.stereotype.Service;

import com.twoday.wms.dto.ProductDto;
import com.twoday.wms.dto.WarehouseDto;
import com.twoday.wms.warehouse.exceptions.ResourceNotFoundException;
import com.twoday.wms.warehouse.product.Product;
import com.twoday.wms.warehouse.product.interfaces.ProductConverter;
import com.twoday.wms.warehouse.warehouse.interfaces.WarehouseConverter;
import com.twoday.wms.warehouse.warehouse.interfaces.WarehouseRepository;
import com.twoday.wms.warehouse.warehouse.interfaces.WarehouseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final ProductConverter productConverter;
    private final WarehouseConverter warehouseConverter;

    @Override
    public List<WarehouseDto> getAllWarehouses() {
        return warehouseRepository.findAll()
                .stream()
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
        return warehouseRepository.findById(id)
                .map(
                        warehouse -> {
                            warehouse.addProduct(product);
                            warehouseRepository.save(warehouse);

                            return productDto;
                        })
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse with id: %s was not found".formatted(id)));
    }

}
