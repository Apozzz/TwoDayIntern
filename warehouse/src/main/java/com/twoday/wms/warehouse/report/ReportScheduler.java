package com.twoday.wms.warehouse.report;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReportScheduler {

    private final ReportSchedulerService reportSchedulerService;

    @Scheduled(cron = "${report.cron.expression}")
    public void generateCsvReport() {
        reportSchedulerService.generateCsvReport();
    }

}
