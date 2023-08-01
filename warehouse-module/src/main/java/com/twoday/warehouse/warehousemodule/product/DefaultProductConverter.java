package com.twoday.warehouse.warehousemodule.product;

import org.springframework.stereotype.Component;

import com.twoday.dto.dtomodule.ProductDto;
import com.twoday.warehouse.warehousemodule.product.interfaces.ProductConverter;

@Component
public class DefaultProductConverter implements ProductConverter {

    @Override
    public ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setQuantity(product.getQuantity());
        
        return productDto;
    }

    @Override
    public Product fromDto(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());

        return product;
    }
    
}
