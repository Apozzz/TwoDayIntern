package com.twoday.warehouse.warehousemodule.report;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.twoday.warehouse.warehousemodule.purchase.Purchase;
import com.twoday.warehouse.warehousemodule.purchase.interfaces.PurchaseService;
import com.twoday.warehouse.warehousemodule.report.interfaces.ReportFileService;
import com.twoday.warehouse.warehousemodule.report.interfaces.ReportGeneratorService;

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
