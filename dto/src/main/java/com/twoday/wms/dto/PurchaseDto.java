package com.twoday.wms.dto;

import java.time.LocalDateTime;

import com.twoday.wms.dto.annotations.CsvField;
import com.twoday.wms.dto.annotations.CsvFields;

public class PurchaseDto {

    @CsvField(name = "Purchase ID", order = 1)
    private Long id;

    @CsvFields({
        @CsvField(name = "User ID", nestedField = "id", order = 2),
        @CsvField(name = "User Name", nestedField = "username", quote = true, order = 3)
    })
    private UserDto user;

    @CsvFields({
        @CsvField(name = "Product ID", nestedField = "id", order = 4),
        @CsvField(name = "Product Name", nestedField = "name", quote = true, order = 5)
    })
    private ProductDto product;

    @CsvField(name = "Quantity", order = 6)
    private Integer quantity;

    @CsvField(name = "Time Stamp", order = 7)
    private LocalDateTime timeStamp;

    public PurchaseDto(Long id, UserDto user, ProductDto product, Integer quantity, LocalDateTime timeStamp) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.timeStamp = timeStamp;
    }

    public PurchaseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "PurchaseDto [id=" + id + ", user=" + user + ", product=" + product + ", quantity=" + quantity
                + ", timeStamp=" + timeStamp + "]";
    }
    
}
