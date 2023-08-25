package com.twoday.wms.shop.purchase;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twoday.wms.dto.PurchaseDto;
import com.twoday.wms.shop.logging.annotations.LogMessage;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private static final String BEFORE_GET_CURRENT_YEAR_RANGE_PURCHASES_LOG = "Fetching purchases for the year range.";
    private static final String AFTER_GET_CURRENT_YEAR_RANGE_PURCHASES_LOG = "Finished fetching purchases for the year range.";
    private static final String BEFORE_GET_CURRENT_MONTH_RANGE_PURCHASES_LOG = "Fetching purchases for the month range.";
    private static final String AFTER_GET_CURRENT_MONTH_RANGE_PURCHASES_LOG = "Finished fetching purchases for the month range.";

    private final PurchaseService purchaseService;

    @GetMapping("/year-range")
    @LogMessage(before = BEFORE_GET_CURRENT_YEAR_RANGE_PURCHASES_LOG, after = AFTER_GET_CURRENT_YEAR_RANGE_PURCHASES_LOG, loggerClass = PurchaseController.class)
    public Mono<ResponseEntity<List<PurchaseDto>>> getYearRangePurchases(@RequestParam(name = "dateTime", required = true) Integer yearDate) {
        return purchaseService.getYearRangePurchases(yearDate);
    }

    @GetMapping("/month-range")
    @LogMessage(before = BEFORE_GET_CURRENT_MONTH_RANGE_PURCHASES_LOG, after = AFTER_GET_CURRENT_MONTH_RANGE_PURCHASES_LOG, loggerClass = PurchaseController.class)
    public Mono<ResponseEntity<List<PurchaseDto>>> getMonthRangePurchases(@RequestParam(name = "dateTime", required = true) String monthDate) {
        return purchaseService.getMonthRangePurchases(monthDate);
    }

}
