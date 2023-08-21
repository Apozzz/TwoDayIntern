package com.twoday.wms.shop.purchase;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.twoday.wms.dto.PurchaseDto;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final WebClient webClient;

    public Mono<ResponseEntity<List<PurchaseDto>>> getCurrentYearRangePurchases() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/purchases/current-year-range")
                        .build())
                .header("Accept", "application/json")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PurchaseDto>>() {
                })
                .map(ResponseEntity::ok);
    }

}
