package com.twoday.wms.warehouse.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twoday.wms.warehouse.product.interfaces.ProductRepository;

public interface JpaProductRepository extends JpaRepository<Product, Long>, ProductRepository {
    
}
