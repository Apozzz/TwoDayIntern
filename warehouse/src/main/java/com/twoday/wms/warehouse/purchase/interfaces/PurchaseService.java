package com.twoday.wms.warehouse.purchase.interfaces;

import java.util.List;

import com.twoday.wms.warehouse.purchase.Purchase;

public interface PurchaseService {
    
    List<Purchase> findTop25ByOrderByIdDesc();

}
