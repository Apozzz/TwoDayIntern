package com.twoday.warehouse.warehousemodule.warehouse;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twoday.dto.dtomodule.ProductDto;
import com.twoday.dto.dtomodule.WarehouseDto;
import com.twoday.warehouse.warehousemodule.product.interfaces.ProductService;
import com.twoday.warehouse.warehousemodule.warehouse.interfaces.WarehouseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<WarehouseDto>> getAllWarehouses() {
        List<WarehouseDto> warehouses = warehouseService.getAllWarehouses();
        return new ResponseEntity<>(warehouses, HttpStatus.OK);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductDto>> getWarehouseProducts(@PathVariable Long id) {
        List<ProductDto> productsDtos = productService.getProductsByWarehouseId(id);
        return new ResponseEntity<>(productsDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WarehouseDto> saveWarehouse(@RequestBody WarehouseDto warehouseDto) {
        WarehouseDto savedWarehouse = warehouseService.saveWarehouse(warehouseDto);
        return new ResponseEntity<>(savedWarehouse, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/products")
    public ResponseEntity<ProductDto> saveWarehouseProduct(@PathVariable Long id,
            @RequestBody ProductDto productDto) {
        return new ResponseEntity<>(warehouseService.saveWarehouseProduct(id, productDto), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/products/{productId}/purchase")
    public ResponseEntity<ProductDto> purchaseProduct(@PathVariable Long id, @PathVariable Long productId,
            @RequestParam("quantity") Integer quantity) {
        ProductDto productDto = productService.purchaseProduct(productId, quantity);
        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }

}
