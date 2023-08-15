package com.twoday.warehouse.warehousemodule.purchase.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.twoday.warehouse.warehousemodule.interfaces.GenericRepository;
import com.twoday.warehouse.warehousemodule.purchase.Purchase;

public interface PurchaseRepository extends GenericRepository<Purchase, Long> {
    
    Purchase save(Purchase purchase);

    @Query("SELECT p FROM Purchase p JOIN FETCH p.user JOIN FETCH p.product")
    List<Purchase> findTop25ByOrderByIdDesc();

}
