package com.twoday.wms.shop.warehouse;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.twoday.wms.shop.pricing.PricingService;
import com.twoday.wms.shop.warehouse.interfaces.WarehouseService;
import com.twoday.wms.dto.ProductDto;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

        private final WebClient webClient;
        private final PricingService pricingService;

        @Override
        public Mono<ResponseEntity<List<ProductDto>>> getProducts(Long id) {
                return webClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/v1/warehouses/{id}/products")
                                                .build(id))
                                .retrieve()
                                .bodyToMono(new ParameterizedTypeReference<List<ProductDto>>() {
                                })
                                .map(productDtos -> {
                                        for (ProductDto productDto : productDtos) {
                                                Float basePrice = productDto.getPrice();
                                                Float priceWithMargin = pricingService
                                                                .calculatePriceWithMargin(basePrice);
                                                productDto.setFinalPrice(priceWithMargin);
                                        }

                                        return ResponseEntity.ok(productDtos);
                                })
                                .onErrorReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }

        @Override
        public Mono<ResponseEntity<ProductDto>> purchaseProduct(Long warehouseId, Long productId, Integer quantity) {
                return webClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/v1/warehouses/{warehouseId}/products/{productId}")
                                                .build(warehouseId, productId))
                                .retrieve()
                                .bodyToMono(ProductDto.class)
                                .flatMap(productDto -> {
                                        Float basePrice = productDto.getPrice();
                                        Float priceWithMargin = pricingService.calculatePriceWithMargin(basePrice);
                                        Float discountedPrice = pricingService.calculateDiscountedPrice(priceWithMargin,
                                                        quantity);
                                        Float finalPrice = pricingService.getFinalPrice(basePrice, discountedPrice);

                                        return webClient.post()
                                                        .uri(uriBuilder -> uriBuilder
                                                                        .path("/v1/warehouses/{warehouseId}/products/{productId}/purchase")
                                                                        .queryParam("quantity", quantity)
                                                                        .queryParam("finalPrice", finalPrice)
                                                                        .build(warehouseId, productId))
                                                        .retrieve()
                                                        .bodyToMono(ProductDto.class)
                                                        .doOnNext(purchasedDto -> purchasedDto
                                                                        .setFinalPrice(finalPrice))
                                                        .map(ResponseEntity::ok);
                                });
        }

}