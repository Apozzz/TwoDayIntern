package com.twoday.warehouse.warehousemodule.product;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.twoday.dto.dtomodule.ProductDto;
import com.twoday.warehouse.warehousemodule.exceptions.ResourceNotFoundException;
import com.twoday.warehouse.warehousemodule.product.exceptions.InsufficientProductException;
import com.twoday.warehouse.warehousemodule.product.interfaces.ProductConverter;
import com.twoday.warehouse.warehousemodule.product.interfaces.ProductRepository;
import com.twoday.warehouse.warehousemodule.product.interfaces.ProductService;
import com.twoday.warehouse.warehousemodule.warehouse.Warehouse;
import com.twoday.warehouse.warehousemodule.warehouse.interfaces.WarehouseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductConverter productConverter;

    @Override
    public ProductDto purchaseProduct(Long id, Integer quantity) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: %s".formatted(id)));

        if (product.getQuantity() < quantity) {
            throw new InsufficientProductException("Not enough product available");
        }

        product.setQuantity(product.getQuantity() - quantity);
        return productConverter.toDto(productRepository.save(product));
    }

    @Override
    public List<ProductDto> getProductsByWarehouseId(Long id) {
        return warehouseRepository.findById(id)
                .map(Warehouse::getProducts)
                .orElse(Collections.emptyList())
                .stream()
                .map(productConverter::toDto)
                .toList();
    }

}
