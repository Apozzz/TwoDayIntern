package com.twoday.wms.warehouse.exceptions.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.twoday.wms.warehouse.exceptions.ResourceNotFoundException;
import com.twoday.wms.warehouse.product.exceptions.InsufficientProductException;
import com.twoday.wms.warehouse.report.exceptions.InvalidDateTimeFormatException;
import com.twoday.wms.warehouse.report.exceptions.ReportFileException;
import com.twoday.wms.warehouse.user.exceptions.UserAlreadyExistsException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        userLogger.error("User Already Exists exception: {}", ex.getMessage(), ex);
        globalLogger.error("User Already Exists exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientProductException.class)
    public ResponseEntity<String> handleInsufficientProductException(InsufficientProductException ex) {
        productLogger.error("Insufficient Product exception: {}", ex.getMessage(), ex);
        globalLogger.error("Insufficient Product exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        globalLogger.error("Resource Not Found exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReportFileException.class)
    public ResponseEntity<String> handleReportFileException(ReportFileException ex) {
        reportLogger.error("Report File exception: {}", ex.getMessage(), ex);
        globalLogger.error("Report File exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidDateTimeFormatException.class)
    public ResponseEntity<String> handleInvalidDateTimeFormatException(InvalidDateTimeFormatException ex) {
        reportLogger.error("Invalid Date Time Format exception: {}", ex.getMessage(), ex);
        globalLogger.error("Invalid Date Time Format exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
