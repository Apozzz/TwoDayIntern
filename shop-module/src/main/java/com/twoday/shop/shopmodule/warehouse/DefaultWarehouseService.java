package com.twoday.shop.shopmodule.warehouse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.twoday.shop.shopmodule.response.ApiResponse;
import com.twoday.shop.shopmodule.warehouse.interfaces.WarehouseService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DefaultWarehouseService implements WarehouseService {

    private final WebClient webClient;

    @Override
    public Mono<ResponseEntity<ApiResponse>> getProducts(Long id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/warehouses/{id}/products")
                        .build(id))
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .map(response -> {
                    if (response.getStatus() == HttpStatus.OK.value()) {
                        return ResponseEntity.status(HttpStatus.OK).body(response);
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                    }
                })
                .onErrorReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null)));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse>> purchaseProduct(Long id, Long productId, Integer quantity) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/warehouses/{id}/products/{productId}/purchase")
                        .queryParam("quantity", quantity)
                        .build(id, productId))
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(ApiResponse.class)
                                .map(apiResponse -> ResponseEntity
                                        .status(apiResponse.getStatus())
                                        .body(apiResponse));
                    } else {
                        return response.createException()
                                .flatMap(ex -> Mono.just(
                                        ResponseEntity
                                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                                .body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                                        "An error occurred while purchasing a product: "
                                                                + ex.getMessage(),
                                                        null))));
                    }
                });
    }

}
