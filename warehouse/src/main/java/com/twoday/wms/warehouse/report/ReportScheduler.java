package com.twoday.wms.warehouse.report;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.twoday.wms.dto.PurchaseDto;
import com.twoday.wms.warehouse.purchase.PurchaseConverter;
import com.twoday.wms.warehouse.purchase.interfaces.PurchaseService;
import com.twoday.wms.warehouse.report.interfaces.ReportFileService;
import com.twoday.wms.warehouse.report.interfaces.ReportGeneratorService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReportScheduler {

    private final PurchaseService purchaseService;
    private final ReportFileService fileService;
    private final ReportGeneratorService generatorService;
    private final PurchaseConverter purchaseConverter;

    @Scheduled(cron = "${report.cron.expression}")
    public void generateCsvReport() {
        List<PurchaseDto> purchases = purchaseService.findTop25ByOrderByIdDesc()
                .stream()
                .map(purchaseConverter::toDto)
                .toList();
        String csvData = generatorService.generateCSV(purchases);
        fileService.saveToFile(csvData);
    }

}
