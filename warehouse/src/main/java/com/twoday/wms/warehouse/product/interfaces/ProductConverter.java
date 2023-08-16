package com.twoday.wms.warehouse.product.interfaces;

import com.twoday.wms.warehouse.interfaces.GenericConverter;
import com.twoday.wms.warehouse.product.Product;
import com.twoday.wms.dto.ProductDto;

public interface ProductConverter extends GenericConverter<Product, ProductDto> {
    
    ProductDto toDto(Product product);
    Product fromDto(ProductDto productDto);

}
