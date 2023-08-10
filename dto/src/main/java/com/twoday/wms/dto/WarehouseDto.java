package com.twoday.wms.dto;

import java.util.List;

public class WarehouseDto {

    private Long id;
    private String name;
    private String location;
    private List<Long> productIds;

    public WarehouseDto(Long id, String name, String location, List<Long> productIds) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.productIds = productIds;
    }

    public WarehouseDto() {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    @Override
    public String toString() {
        return "WarehouseDto [id=" + id + ", name=" + name + ", location=" + location + ", productIds=" + productIds
                + "]";
    }

}
