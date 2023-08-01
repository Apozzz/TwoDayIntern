package com.twoday.shop.shopmodule.warehouse.interfaces;

import org.springframework.http.ResponseEntity;

import com.twoday.shop.shopmodule.response.ApiResponse;

import reactor.core.publisher.Mono;

public interface WarehouseService {
    
    Mono<ResponseEntity<ApiResponse>> getProducts(Long id);
    Mono<ResponseEntity<ApiResponse>> purchaseProduct(Long id, Long productId, Integer quantity);

}
