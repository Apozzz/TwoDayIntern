package com.twoday.warehouse.warehousemodule.report.interfaces;

import java.util.List;

import com.twoday.warehouse.warehousemodule.purchase.Purchase;

public interface ReportGeneratorService {
    
    String generateCSV(List<Purchase> purchases);

}
