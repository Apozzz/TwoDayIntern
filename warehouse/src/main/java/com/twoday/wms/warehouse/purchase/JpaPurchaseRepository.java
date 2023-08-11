package com.twoday.wms.warehouse.purchase;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twoday.wms.warehouse.purchase.interfaces.PurchaseRepository;

public interface JpaPurchaseRepository extends JpaRepository<Purchase, Long>, PurchaseRepository {
    
}
