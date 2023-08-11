package com.twoday.wms.warehouse.unit.warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.twoday.wms.dto.WarehouseDto;
import com.twoday.wms.warehouse.product.Product;
import com.twoday.wms.warehouse.warehouse.Warehouse;
import com.twoday.wms.warehouse.warehouse.WarehouseConverterImpl;

import jakarta.persistence.EntityManager;

public class WarehouseConverterImplTest {

    @InjectMocks
    private WarehouseConverterImpl warehouseConverter;

    @Mock
    private EntityManager entityManager; 

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testToDto() {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1L);
        warehouse.setName("Sample Warehouse");
        warehouse.setLocation("Sample Location");
        Product product1 = new Product();
        product1.setId(5L);
        Product product2 = new Product();
        product2.setId(6L);
        warehouse.setProducts(Arrays.asList(product1, product2));
        WarehouseDto dto = warehouseConverter.toDto(warehouse);
        assertEquals(warehouse.getId(), dto.getId());
        assertEquals(warehouse.getName(), dto.getName());
        assertEquals(warehouse.getLocation(), dto.getLocation());
        List<Long> expectedProductIds = Arrays.asList(5L, 6L);
        assertEquals(expectedProductIds, dto.getProductIds());
    }

    @Test
    public void testFromDto() {
        Product productOne = new Product(1L, "Product1", "Desc", 9.99f, 10);
        Product productTwo = new Product(2L, "Product2", "Desc", 7.99f, 10);
        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setId(1L);
        warehouseDto.setName("TestWarehouse");
        warehouseDto.setLocation("TestLocation");
        warehouseDto.setProductIds(Arrays.asList(1L, 2L));
        when(entityManager.getReference(Product.class, 1L)).thenReturn(productOne);
        when(entityManager.getReference(Product.class, 2L)).thenReturn(productTwo);
        Warehouse warehouse = warehouseConverter.fromDto(warehouseDto);
        assertNotNull(warehouse);
        assertEquals(warehouseDto.getId(), warehouse.getId());
        assertEquals(warehouseDto.getName(), warehouse.getName());
        assertEquals(warehouseDto.getLocation(), warehouse.getLocation());
        assertTrue(warehouse.getProducts().contains(productOne));
        assertTrue(warehouse.getProducts().contains(productTwo));
    }
}