package com.twoday.warehouse.warehousemodule.product.interfaces;

import java.util.List;
import java.util.Optional;

import com.twoday.warehouse.warehousemodule.interfaces.GenericRepository;
import com.twoday.warehouse.warehousemodule.product.Product;

public interface ProductRepository extends GenericRepository<Product, Long> {
    
    Optional<Product> findById(Long id);
    Product save(Product product);
    List<Product> findAllById(Iterable<Long> ids);
    void deleteAllById(Iterable<? extends Long> ids);
    Optional<Product> findByName(String name);

}
