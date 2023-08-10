package com.twoday.wms.shop.warehouse;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twoday.wms.dto.ProductDto;
import com.twoday.wms.shop.warehouse.interfaces.WarehouseService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping("/{id}/products")
    public Mono<ResponseEntity<List<ProductDto>>> getProducts(@PathVariable Long id) {
        return warehouseService.getProducts(id);
    }

    @PostMapping("/{id}/products/{productId}/purchase")
    public Mono<ResponseEntity<ProductDto>> purchaseProduct(@PathVariable Long id, @PathVariable Long productId,
            @RequestParam("quantity") Integer quantity) {
        return warehouseService.purchaseProduct(id, productId, quantity);
    }

}
