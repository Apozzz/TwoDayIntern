package com.twoday.warehouse.warehousemodule.purchase;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twoday.warehouse.warehousemodule.purchase.interfaces.PurchaseRepository;

public interface JpaPurchaseRepository extends JpaRepository<Purchase, Long>, PurchaseRepository {
    
}
