package com.twoday.shop.shopmodule.unit.warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.twoday.dto.dtomodule.ProductDto;
import com.twoday.shop.shopmodule.warehouse.WarehouseController;
import com.twoday.shop.shopmodule.warehouse.interfaces.WarehouseService;

import reactor.core.publisher.Mono;

public class WarehouseControllerTest {

    @Mock
    private WarehouseService warehouseService;

    @InjectMocks
    private WarehouseController warehouseController;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(warehouseController).build();
    }

    @Test
    public void testGetProducts() {
        Long warehouseId = 1L;
        ProductDto mockProduct = new ProductDto(1L, "Name", "Desc", 0.99f, 10);
        List<ProductDto> mockProducts = Arrays.asList(mockProduct);
        when(warehouseService.getProducts(warehouseId)).thenReturn(Mono.just(ResponseEntity.ok(mockProducts)));

        webTestClient.get()
                .uri("/v1/warehouses/{id}/products", warehouseId)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProductDto.class)
                .hasSize(1)
                .value(products -> {
                    ProductDto actualProduct = products.get(0);
                    assertEquals(mockProduct.getId(), actualProduct.getId());
                    assertEquals(mockProduct.getName(), actualProduct.getName());
                    assertEquals(mockProduct.getDescription(), actualProduct.getDescription());
                    assertEquals(mockProduct.getPrice(), actualProduct.getPrice());
                    assertEquals(mockProduct.getQuantity(), actualProduct.getQuantity());
                });

        verify(warehouseService, times(1)).getProducts(warehouseId);
    }

    @Test
    public void testPurchaseProduct() {
        Long warehouseId = 1L;
        Long productId = 2L;
        Integer quantity = 5;
        ProductDto mockProduct = new ProductDto(1L, "Name", "Desc", 0.99f, 10);
        when(warehouseService.purchaseProduct(warehouseId, productId, quantity))
                .thenReturn(Mono.just(ResponseEntity.ok(mockProduct)));

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/warehouses/{id}/products/{productId}/purchase")
                        .queryParam("quantity", quantity)
                        .build(warehouseId, productId))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductDto.class)
                .value(actualProduct -> {
                    assertEquals(mockProduct.getId(), actualProduct.getId());
                    assertEquals(mockProduct.getName(), actualProduct.getName());
                    assertEquals(mockProduct.getDescription(), actualProduct.getDescription());
                    assertEquals(mockProduct.getPrice(), actualProduct.getPrice());
                    assertEquals(mockProduct.getQuantity(), actualProduct.getQuantity());
                });

        verify(warehouseService, times(1)).purchaseProduct(warehouseId, productId, quantity);
    }
}