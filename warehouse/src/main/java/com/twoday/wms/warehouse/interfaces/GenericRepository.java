package com.twoday.wms.warehouse.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GenericRepository<T, ID extends Serializable> {

    List<T> findAll();
    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAllById(Iterable<ID> ids);
    void deleteAll();
    
}
