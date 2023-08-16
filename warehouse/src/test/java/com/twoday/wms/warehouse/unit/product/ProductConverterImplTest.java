package com.twoday.wms.warehouse.unit.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.twoday.wms.warehouse.product.Product;
import com.twoday.wms.warehouse.product.ProductConverterImpl;
import com.twoday.wms.dto.ProductDto;

public class ProductConverterImplTest {
    
    @InjectMocks
    private ProductConverterImpl productConverter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testToDto() {
        Product product = new Product(1L, "Sample Name", "Sample Desc", 9.99f, 10);
        ProductDto dto = productConverter.toDto(product);
        assertNotNull(dto);
        assertEquals(product.getId(), dto.getId());
        assertEquals(product.getName(), dto.getName());
        assertEquals(product.getDescription(), dto.getDescription());
        assertEquals(product.getPrice(), dto.getPrice());
        assertEquals(product.getQuantity(), dto.getQuantity());
    }

    @Test
    public void testFromDto() {
        ProductDto ProductDto = new ProductDto(1L, "Sample Name", "Sample Desc", 9.99f, 10);
        Product product = productConverter.fromDto(ProductDto);
        assertNotNull(product);
        assertEquals(ProductDto.getId(), product.getId());
        assertEquals(ProductDto.getName(), product.getName());
        assertEquals(ProductDto.getDescription(), product.getDescription());
        assertEquals(ProductDto.getPrice(), product.getPrice());
        assertEquals(ProductDto.getQuantity(), product.getQuantity());
    }

}
