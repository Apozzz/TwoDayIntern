package com.twoday.warehouse.warehousemodule.product;

import org.springframework.stereotype.Service;

import com.twoday.warehouse.warehousemodule.exceptions.ResourceNotFoundException;
import com.twoday.warehouse.warehousemodule.product.exceptions.InsufficientProductException;
import com.twoday.warehouse.warehousemodule.product.interfaces.ProductRepository;
import com.twoday.warehouse.warehousemodule.product.interfaces.ProductService;

@Service
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    public DefaultProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product purchaseProduct(Long id, Integer quantity) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: %s".formatted(id)));

        if (product.getQuantity() < quantity) {
            throw new InsufficientProductException("Not enough product available");
        }

        product.setQuantity(product.getQuantity() - quantity);
        return productRepository.save(product);
    }

}
