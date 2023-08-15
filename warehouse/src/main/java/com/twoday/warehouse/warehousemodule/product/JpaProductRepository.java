package com.twoday.warehouse.warehousemodule.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twoday.warehouse.warehousemodule.product.interfaces.ProductRepository;

public interface JpaProductRepository extends JpaRepository<Product, Long>, ProductRepository {
    
}
