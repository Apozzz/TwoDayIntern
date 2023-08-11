package com.twoday.wms.warehouse.product.interfaces;

import java.util.List;

import com.twoday.wms.dto.ProductDto;

public interface ProductService {
    
    ProductDto purchaseProduct(Long id, Integer quantity, String username);
    List<ProductDto> getProductsByWarehouseId(Long id);
    ProductDto saveProductByWarehouseId(Long id, ProductDto productDto);

}
