package com.twoday.wms.warehouse.interfaces;

public interface GenericConverter<T, D> {
    
    D toDto(T entity);
    T fromDto(D dto);

}
