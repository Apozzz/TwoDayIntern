package com.twoday.wms.warehouse.unit.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.twoday.wms.dto.ProductDto;
import com.twoday.wms.warehouse.exceptions.ResourceNotFoundException;
import com.twoday.wms.warehouse.product.Product;
import com.twoday.wms.warehouse.product.ProductServiceImpl;
import com.twoday.wms.warehouse.product.exceptions.InsufficientProductException;
import com.twoday.wms.warehouse.product.interfaces.ProductConverter;
import com.twoday.wms.warehouse.product.interfaces.ProductRepository;
import com.twoday.wms.warehouse.purchase.Purchase;
import com.twoday.wms.warehouse.purchase.interfaces.PurchaseRepository;
import com.twoday.wms.warehouse.user.User;
import com.twoday.wms.warehouse.user.interfaces.UserRepository;
import com.twoday.wms.warehouse.warehouse.Warehouse;
import com.twoday.wms.warehouse.warehouse.interfaces.WarehouseRepository;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private ProductConverter productConverter;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PurchaseRepository purchaseRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private Warehouse warehouse;
    private ProductDto productDto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        product = new Product(1L, "Name", "Desc", 9.99f, 10);
        warehouse = new Warehouse(1L, "Name", "Location", new ArrayList<>());
        productDto = new ProductDto(1L, "Name", "Desc", 9.99f, 5);
    }

    @Test
    void testPurchaseProductSuccess() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        User user = new User(1L, "username", "password");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(productRepository.save(product)).thenReturn(product);
        when(productConverter.toDto(any(Product.class))).thenReturn(productDto);
        ProductDto result = productService.purchaseProduct(product.getId(), 5, user.getUsername());
        assertEquals(productDto, result);
        verify(purchaseRepository, times(1)).save(any(Purchase.class));
    }

    @Test
    void testPurchaseProductProductNotFound() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            productService.purchaseProduct(product.getId(), 5, "testUser");
        });
    }

    @Test
    void testPurchaseProductInsufficientQuantity() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        assertThrows(InsufficientProductException.class, () -> {
            productService.purchaseProduct(product.getId(), product.getQuantity() + 5, "testUser");
        });
    }

    @Test
    void testPurchaseProductUserNotFound() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        String username = "nonExistentUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> {
            productService.purchaseProduct(product.getId(), 5, username);
        });
    }

    @Test
    void testGetProductsByWarehouseId() {
        warehouse.setProducts(Arrays.asList(product));
        when(warehouseRepository.findById(warehouse.getId())).thenReturn(Optional.of(warehouse));
        ProductDto productDto = new ProductDto(1L, "Name", "Desc", 9.99f, 5);
        when(productConverter.toDto(product)).thenReturn(productDto);
        List<ProductDto> result = productService.getProductsByWarehouseId(warehouse.getId());
        assertTrue(result.contains(productDto));
    }

    @Test
    void testSaveProductByWarehouseId() {
        when(productConverter.fromDto(productDto)).thenReturn(product);
        when(warehouseRepository.findById(warehouse.getId())).thenReturn(Optional.of(warehouse));
        ProductDto returnedProductDto = productService.saveProductByWarehouseId(warehouse.getId(), productDto);
        assertEquals(productDto, returnedProductDto);
    }

    @Test
    public void testSaveProductByWarehouseIdWarehouseNotFound() {
        when(warehouseRepository.findById(warehouse.getId())).thenReturn(Optional.empty());
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.saveProductByWarehouseId(warehouse.getId(), productDto);
        });
        assertTrue(exception.getMessage().contains("Warehouse with id: " + warehouse.getId() + " was not found"));
    }

}
