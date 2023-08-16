package com.twoday.wms.warehouse.report.interfaces;

import java.util.List;

import com.twoday.wms.warehouse.purchase.Purchase;

public interface ReportGeneratorService {
    
    String generateCSV(List<Purchase> purchases);

}
