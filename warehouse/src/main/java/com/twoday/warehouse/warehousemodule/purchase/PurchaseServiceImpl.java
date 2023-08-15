package com.twoday.warehouse.warehousemodule.purchase;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.twoday.warehouse.warehousemodule.purchase.interfaces.PurchaseRepository;
import com.twoday.warehouse.warehousemodule.purchase.interfaces.PurchaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    
    @Cacheable(value = "topPurchases")
    public List<Purchase> findTop25ByOrderByIdDesc() {
        return purchaseRepository.findTop25ByOrderByIdDesc();
    }

}
