package com.twoday.warehouse.warehousemodule.product.interfaces;

import java.util.List;

import com.twoday.dto.dtomodule.ProductDto;

public interface ProductService {
    
    ProductDto purchaseProduct(Long id, Integer quantity, String username);
    List<ProductDto> getProductsByWarehouseId(Long id);
    ProductDto saveProductByWarehouseId(Long id, ProductDto productDto);

}
