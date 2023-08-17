package com.twoday.wms.warehouse.report;

import java.util.List;

import org.springframework.stereotype.Service;

import com.twoday.wms.dto.PurchaseDto;
import com.twoday.wms.warehouse.purchase.PurchaseConverter;
import com.twoday.wms.warehouse.purchase.interfaces.PurchaseService;
import com.twoday.wms.warehouse.report.interfaces.ReportFileService;
import com.twoday.wms.warehouse.report.interfaces.ReportGeneratorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportSchedulerService {

    private final PurchaseService purchaseService;
    private final ReportFileService fileService;
    private final ReportGeneratorService generatorService;
    private final PurchaseConverter purchaseConverter;

    public void generateCsvReport() {
        log.info("Scheduled report generation job started.");
        List<PurchaseDto> purchases = purchaseService.findTop25ByOrderByIdDesc()
                .stream()
                .map(purchaseConverter::toDto)
                .toList();
        log.info("Fetched {} purchases for report generation.", purchases.size());
        String csvData = generatorService.generateCSV(purchases);
        fileService.saveToFile(csvData);
        log.info("Report generated and saved successfully.");
    }

}
