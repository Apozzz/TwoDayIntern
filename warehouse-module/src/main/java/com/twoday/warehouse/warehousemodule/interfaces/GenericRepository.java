package com.twoday.warehouse.warehousemodule.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GenericRepository<T, ID extends Serializable> {

    List<T> findAll();

    T save(T entity);

    Optional<T> findById(ID id);
    
}
