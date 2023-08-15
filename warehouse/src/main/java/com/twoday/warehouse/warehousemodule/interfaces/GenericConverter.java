package com.twoday.warehouse.warehousemodule.interfaces;

public interface GenericConverter<T, D> {
    
    D toDto(T entity);
    T fromDto(D dto);

}
