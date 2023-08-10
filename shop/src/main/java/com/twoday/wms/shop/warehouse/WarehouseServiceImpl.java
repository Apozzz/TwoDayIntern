package com.twoday.wms.shop.warehouse;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.twoday.wms.dto.ProductDto;
import com.twoday.wms.shop.warehouse.interfaces.WarehouseService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

        private final WebClient webClient;

        @Override
        public Mono<ResponseEntity<List<ProductDto>>> getProducts(Long id) {
                return webClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/v1/warehouses/{id}/products")
                                                .build(id))
                                .retrieve()
                                .bodyToMono(new ParameterizedTypeReference<List<ProductDto>>() {
                                })
                                .map(products -> ResponseEntity.ok(products))
                                .onErrorReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }

        @Override
        public Mono<ResponseEntity<ProductDto>> purchaseProduct(Long id, Long productId, Integer quantity) {
                return webClient.post()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/v1/warehouses/{id}/products/{productId}/purchase")
                                                .queryParam("quantity", quantity)
                                                .build(id, productId))
                                .exchangeToMono(response -> {
                                        if (response.statusCode().is2xxSuccessful()) {
                                                return response.bodyToMono(ProductDto.class)
                                                                .map(ResponseEntity::ok);
                                        } else {
                                                return response.createException()
                                                                .flatMap(ex -> Mono.just(
                                                                                ResponseEntity.status(
                                                                                                HttpStatus.INTERNAL_SERVER_ERROR)
                                                                                                .build()));
                                        }
                                });
        }

}
