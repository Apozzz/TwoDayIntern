package com.twoday.warehouse.warehousemodule.product.exceptions;

public class InsufficientProductException extends RuntimeException {
    
    public InsufficientProductException(String message) {
        super(message);
    }

}
