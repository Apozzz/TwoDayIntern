package com.twoday.wms.warehouse.product;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.twoday.wms.dto.ProductDto;
import com.twoday.wms.warehouse.exceptions.ResourceNotFoundException;
import com.twoday.wms.warehouse.product.exceptions.InsufficientProductException;
import com.twoday.wms.warehouse.product.interfaces.ProductConverter;
import com.twoday.wms.warehouse.product.interfaces.ProductRepository;
import com.twoday.wms.warehouse.product.interfaces.ProductService;
import com.twoday.wms.warehouse.warehouse.Warehouse;
import com.twoday.wms.warehouse.warehouse.interfaces.WarehouseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductConverter productConverter;

    @Override
    public ProductDto purchaseProduct(Long id, Integer quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: %s".formatted(id)));

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
