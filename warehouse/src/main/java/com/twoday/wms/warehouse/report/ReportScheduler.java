package com.twoday.wms.warehouse.report;

import java.io.IOException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReportScheduler {

    private final ReportSchedulerService reportSchedulerService;

    @Scheduled(cron = "${report.cron.expression}")
    public void generateCsvReport() throws IOException {
        reportSchedulerService.generateCsvReport();
    }

}
