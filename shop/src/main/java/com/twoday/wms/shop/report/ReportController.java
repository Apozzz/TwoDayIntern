package com.twoday.wms.shop.report;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twoday.wms.shop.logging.annotations.LogMessage;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    static final String BEFORE_GET_PURCHASES_REPORT_LOG = "Fetching purchases report with Date-Time: {}";
    static final String AFTER_GET_PURCHASES_REPORT_LOG = "Successfully fetched purchases report with Date-Time: {}.";

    private final ReportService reportService;

    @GetMapping("/purchases/csv")
    @LogMessage(before = BEFORE_GET_PURCHASES_REPORT_LOG, after = AFTER_GET_PURCHASES_REPORT_LOG, loggerClass = ReportController.class)
    public Mono<ResponseEntity<?>> getPurchasesReport(
            @RequestParam(name = "dateTime", required = false) String dateTime) {
        return reportService.getPurchaseReport(dateTime);
    }

}
