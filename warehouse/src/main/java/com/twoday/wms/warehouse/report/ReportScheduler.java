package com.twoday.wms.warehouse.report;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.twoday.wms.warehouse.purchase.Purchase;
import com.twoday.wms.warehouse.purchase.interfaces.PurchaseService;
import com.twoday.wms.warehouse.report.interfaces.ReportFileService;
import com.twoday.wms.warehouse.report.interfaces.ReportGeneratorService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReportScheduler {

    private static final String HOURLY_CRON_EXPRESSION = "0 0 * * * ?";

    private final PurchaseService purchaseService;
    private final ReportFileService fileService;
    private final ReportGeneratorService generatorService;

    @Scheduled(cron = HOURLY_CRON_EXPRESSION)
    public void generateCsvReport() {
        List<Purchase> purchases = purchaseService.findTop25ByOrderByIdDesc();
        String csvData = generatorService.generateCSV(purchases);
        fileService.saveToFile(csvData);
    }

}
