package com.twoday.warehouse.warehousemodule.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.twoday.warehouse.warehousemodule.exceptions.ResourceNotFoundException;
import com.twoday.warehouse.warehousemodule.product.exceptions.InsufficientProductException;
import com.twoday.warehouse.warehousemodule.report.exceptions.InvalidDateTimeFormatException;
import com.twoday.warehouse.warehousemodule.report.exceptions.ReportFileException;
import com.twoday.warehouse.warehousemodule.user.exceptions.UserAlreadyExistsException;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientProductException.class)
    public ResponseEntity<String> handleInsufficientProductException(InsufficientProductException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReportFileException.class)
    public ResponseEntity<String> handleReportFileException(ReportFileException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidDateTimeFormatException.class)
    public ResponseEntity<String> handleInvalidDateTimeFormatException(InvalidDateTimeFormatException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
