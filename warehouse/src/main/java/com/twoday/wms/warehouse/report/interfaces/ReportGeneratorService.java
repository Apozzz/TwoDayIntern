package com.twoday.wms.warehouse.report.interfaces;

import java.util.List;

import com.twoday.wms.dto.PurchaseDto;

public interface ReportGeneratorService {
    
    String generateCSV(List<PurchaseDto> purchases);

}
