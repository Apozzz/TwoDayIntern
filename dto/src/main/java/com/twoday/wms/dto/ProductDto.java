package com.twoday.wms.dto;

public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private Float price;
    private Float finalPrice;
    private Integer quantity;

    public ProductDto(Long id, String name, String description, Float price, Float finalPrice, Integer quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.finalPrice = finalPrice;
        this.quantity = quantity;
    }

    public ProductDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Float finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductDto [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
                + ", final price=" + finalPrice + ", quantity=" + quantity + "]";
    }

}
