package com.twoday.wms.warehouse.product;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.twoday.wms.warehouse.product.interfaces.ProductConverter;
import com.twoday.wms.dto.ProductDto;

@Component
public class ProductConverterImpl implements ProductConverter {

    @Override
    public ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }

    @Override
    public Product fromDto(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }

}
