package com.twoday.warehouse.warehousemodule.warehouse;

import org.springframework.dao.DataIntegrityViolationException;
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
import com.twoday.warehouse.warehousemodule.exceptions.ResourceNotFoundException;
import com.twoday.warehouse.warehousemodule.product.Product;
import com.twoday.warehouse.warehousemodule.product.exceptions.InsufficientProductException;
import com.twoday.warehouse.warehousemodule.product.interfaces.ProductService;
import com.twoday.warehouse.warehousemodule.response.ApiResponse;
import com.twoday.warehouse.warehousemodule.warehouse.interfaces.WarehouseService;

@RestController
@RequestMapping("/v1/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;
    private final ProductService productService;

    public WarehouseController(WarehouseService warehouseService, ProductService productService) {
        this.warehouseService = warehouseService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllWarehouses() {
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), null, warehouseService.getAllWarehouses()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<ApiResponse> getWarehouseProducts(@PathVariable Long id) {
        return new ResponseEntity<>(
                new ApiResponse(HttpStatus.OK.value(), null, warehouseService.getProductsByWarehouseId(id)),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> saveWarehouse(@RequestBody WarehouseDto warehouseDto) {
        return new ResponseEntity<>(new ApiResponse(HttpStatus.CREATED.value(), "Warehouse was successfully created.",
                warehouseService.saveWarehouse(warehouseDto)), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/products")
    public ResponseEntity<ApiResponse> saveWarehouseProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        try {
            WarehouseDto warehouseDto = warehouseService.saveWarehouseProduct(id, productDto);
            return new ResponseEntity<>(
                    new ApiResponse(HttpStatus.CREATED.value(), "Product added to Warehouse successfully", warehouseDto),
                    HttpStatus.CREATED);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null),
                    HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), null),
                    HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/{id}/products/{productId}/purchase")
    public ResponseEntity<ApiResponse> purchaseProduct(@PathVariable Long id, @PathVariable Long productId,
            @RequestParam("quantity") Integer quantity) {
        try {
            Product product = productService.purchaseProduct(productId, quantity);
            return new ResponseEntity<>(
                    new ApiResponse(HttpStatus.CREATED.value(), "Product was purchased successfully", product),
                    HttpStatus.CREATED);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null),
                    HttpStatus.NOT_FOUND);
        } catch (InsufficientProductException ex) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
