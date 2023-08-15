package com.twoday.warehouse.warehousemodule.product.interfaces;

import com.twoday.dto.dtomodule.ProductDto;
import com.twoday.warehouse.warehousemodule.interfaces.GenericConverter;
import com.twoday.warehouse.warehousemodule.product.Product;

public interface ProductConverter extends GenericConverter<Product, ProductDto> {
    
    ProductDto toDto(Product product);
    Product fromDto(ProductDto productDto);

}
