package com.twoday.wms.warehouse.product.exceptions;

public class InsufficientProductException extends RuntimeException {
    
    public InsufficientProductException(String message) {
        super(message);
    }

}
