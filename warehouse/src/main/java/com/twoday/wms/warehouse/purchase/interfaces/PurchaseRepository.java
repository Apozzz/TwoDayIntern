package com.twoday.wms.warehouse.purchase.interfaces;

import com.twoday.wms.warehouse.interfaces.GenericRepository;
import com.twoday.wms.warehouse.purchase.Purchase;

public interface PurchaseRepository extends GenericRepository<Purchase, Long> {
    
    Purchase save(Purchase purchase);

}
