package com.twoday.warehouse.warehousemodule.product;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.twoday.dto.dtomodule.ProductDto;
import com.twoday.warehouse.warehousemodule.exceptions.ResourceNotFoundException;
import com.twoday.warehouse.warehousemodule.product.exceptions.InsufficientProductException;
import com.twoday.warehouse.warehousemodule.product.interfaces.ProductConverter;
import com.twoday.warehouse.warehousemodule.product.interfaces.ProductRepository;
import com.twoday.warehouse.warehousemodule.product.interfaces.ProductService;
import com.twoday.warehouse.warehousemodule.purchase.Purchase;
import com.twoday.warehouse.warehousemodule.purchase.interfaces.PurchaseRepository;
import com.twoday.warehouse.warehousemodule.user.User;
import com.twoday.warehouse.warehousemodule.user.interfaces.UserRepository;
import com.twoday.warehouse.warehousemodule.warehouse.Warehouse;
import com.twoday.warehouse.warehousemodule.warehouse.interfaces.WarehouseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductConverter productConverter;
    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;

    @Override
    public ProductDto purchaseProduct(Long id, Integer quantity, String username) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: %s".formatted(id)));

        if (product.getQuantity() < quantity) {
            throw new InsufficientProductException("Not enough product available");
        }

        product.setQuantity(product.getQuantity() - quantity);
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        Purchase purchase = new Purchase(user, product, quantity, LocalDateTime.now());
        purchaseRepository.save(purchase);

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

    @Override
    public ProductDto saveProductByWarehouseId(Long id, ProductDto productDto) {
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
