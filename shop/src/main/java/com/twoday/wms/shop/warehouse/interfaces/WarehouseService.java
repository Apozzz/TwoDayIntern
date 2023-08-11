package com.twoday.wms.shop.warehouse.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.twoday.wms.dto.ProductDto;

import reactor.core.publisher.Mono;

public interface WarehouseService {
    
    Mono<ResponseEntity<List<ProductDto>>> getProducts(Long id);
    Mono<ResponseEntity<ProductDto>> purchaseProduct(Long id, Long productId, Integer quantity);

}