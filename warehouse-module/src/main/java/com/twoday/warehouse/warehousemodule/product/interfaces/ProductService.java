package com.twoday.warehouse.warehousemodule.product.interfaces;

import com.twoday.warehouse.warehousemodule.product.Product;

public interface ProductService {
    
    Product purchaseProduct(Long id, Integer quantity);

}
