package com.twoday.wms.shop.warehouse;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twoday.wms.shop.logging.annotations.LogMessage;
import com.twoday.wms.shop.warehouse.interfaces.WarehouseService;
import com.twoday.wms.dto.ProductDto;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    static final String BEFORE_GET_PRODUCTS_LOG = "Fetching products for warehouse {}...";
    static final String AFTER_GET_PRODUCTS_LOG = "Successfully fetched products for warehouse {}.";
    static final String BEFORE_PURCHASE_PRODUCT_LOG = "Attempting to purchase from warehouse {} product {} with quantity {}...";
    static final String AFTER_PURCHASE_PRODUCT_LOG = "Successfully purchased from warehouse {} product {} quantity {}.";

    private final WarehouseService warehouseService;

    @GetMapping("/{id}/products")
    @LogMessage(before = BEFORE_GET_PRODUCTS_LOG, after = AFTER_GET_PRODUCTS_LOG)
    public Mono<ResponseEntity<List<ProductDto>>> getProducts(@PathVariable Long id) {
        return warehouseService.getProducts(id);
    }

    @PostMapping("/{id}/products/{productId}/purchase")
    @LogMessage(before = BEFORE_PURCHASE_PRODUCT_LOG, after = AFTER_PURCHASE_PRODUCT_LOG)
    public Mono<ResponseEntity<ProductDto>> purchaseProduct(@PathVariable Long id, @PathVariable Long productId,
            @RequestParam("quantity") Integer quantity) {
        return warehouseService.purchaseProduct(id, productId, quantity);
    }

}
