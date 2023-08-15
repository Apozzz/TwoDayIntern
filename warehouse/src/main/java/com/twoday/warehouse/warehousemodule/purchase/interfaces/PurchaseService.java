package com.twoday.warehouse.warehousemodule.purchase.interfaces;

import java.util.List;

import com.twoday.warehouse.warehousemodule.purchase.Purchase;

public interface PurchaseService {
    
    List<Purchase> findTop25ByOrderByIdDesc();

}
