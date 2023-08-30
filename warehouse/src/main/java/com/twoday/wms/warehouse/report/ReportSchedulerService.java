package com.twoday.wms.warehouse.report;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.twoday.wms.dto.PurchaseDto;
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

    public void generateCsvReport() throws IOException {
        LocalDateTime startDate = fileService.getLastGeneratedStandardReportDate();
        LocalDateTime endDate = LocalDateTime.now();
        log.info("Scheduled report generation job started.");
        List<PurchaseDto> purchases = purchaseService.findAllWithTimestampBetween(startDate, endDate);
        log.info("Fetched {} purchases for report generation.", purchases.size());
        String csvData = generatorService.generateCSV(purchases);
        fileService.saveToFile(csvData);
        log.info("Report generated and saved successfully.");
    }

}
