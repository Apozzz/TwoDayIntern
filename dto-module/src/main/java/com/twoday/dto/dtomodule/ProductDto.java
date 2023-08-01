package com.twoday.dto.dtomodule;

public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private Float price;
    private Integer quantity;
    private Long warehouseId;

    public ProductDto(Long id, String name, String description, Float price, Integer quantity, Long warehouseId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.warehouseId = warehouseId;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    @Override
    public String toString() {
        return "ProductDto [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
                + ", quantity=" + quantity + ", warehouseId=" + warehouseId + "]";
    }

}
