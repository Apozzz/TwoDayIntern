package com.twoday.wms.warehouse.warehouse;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twoday.wms.dto.ProductDto;
import com.twoday.wms.dto.WarehouseDto;
import com.twoday.wms.warehouse.product.interfaces.ProductService;
import com.twoday.wms.warehouse.warehouse.interfaces.WarehouseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/warehouses")
@RequiredArgsConstructor
@Slf4j
public class WarehouseController {

    private final WarehouseService warehouseService;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<WarehouseDto>> getAllWarehouses() {
        log.info("Fetching all warehouses...");
        List<WarehouseDto> warehouses = warehouseService.getAllWarehouses();
        log.info("Fetched {} warehouses successfully.", warehouses.size());
        return new ResponseEntity<>(warehouses, HttpStatus.OK);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductDto>> getWarehouseProducts(@PathVariable Long id) {
        log.info("Fetching products for warehouse ID: {}...", id);
        List<ProductDto> productsDtos = productService.getProductsByWarehouseId(id);
        log.info("Fetched {} products for warehouse ID: {} successfully.", productsDtos.size(), id);
        return new ResponseEntity<>(productsDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WarehouseDto> saveWarehouse(@RequestBody WarehouseDto warehouseDto) {
        log.info("Attempting to save warehouse with details: {}", warehouseDto);
        WarehouseDto savedWarehouse = warehouseService.saveWarehouse(warehouseDto);
        log.info("Successfully saved warehouse with ID: {}.", savedWarehouse.getId());
        return new ResponseEntity<>(savedWarehouse, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/products")
    public ResponseEntity<ProductDto> saveWarehouseProduct(@PathVariable Long id,
            @RequestBody ProductDto productDto) {
        log.info("Attempting to save product with details: {} for warehouse ID: {}...", productDto, id);
        ProductDto savedProduct = productService.saveProductByWarehouseId(id, productDto);
        log.info("Successfully saved product with ID: {} for warehouse ID: {}.", savedProduct.getId(), id);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/products/{productId}/purchase")
    public ResponseEntity<ProductDto> purchaseProduct(@PathVariable Long id, @PathVariable Long productId,
            @RequestParam("quantity") Integer quantity, @RequestParam("finalPrice") Float finalPrice) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        log.info(
                "User {} is attempting to purchase product ID: {} from warehouse ID: {} with quantity: {} and final product price: {}...",
                currentPrincipalName, productId, id, quantity, finalPrice);
        ProductDto productDto = productService.purchaseProduct(productId, quantity, currentPrincipalName, finalPrice);
        log.info("User {} successfully purchased product ID: {} from warehouse ID: {}.", currentPrincipalName,
                productId, id);
        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/products/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id, @PathVariable Long productId) {
        log.info("Fetching product with ID: {} for warehouse ID: {}...", productId, id);
        ProductDto productsDto = productService.getProduct(productId);
        log.info("Fetched product with ID: {} for warehouse ID: {} successfully.", productId, id);
        return new ResponseEntity<>(productsDto, HttpStatus.OK);
    }

}
