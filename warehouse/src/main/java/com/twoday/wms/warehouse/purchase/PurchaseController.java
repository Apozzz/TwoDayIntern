package com.twoday.wms.warehouse.purchase;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twoday.wms.dto.PurchaseDto;
import com.twoday.wms.warehouse.purchase.interfaces.PurchaseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/purchases")
@RequiredArgsConstructor
@Slf4j
public class PurchaseController {
    
    private final PurchaseService purchaseService;

    @GetMapping("/year-range")
    public ResponseEntity<List<PurchaseDto>> getCurrentYearRangePurchases(@RequestParam(name = "dateTime", required = true) Integer dateYear) {
        log.info("Received request to fetch purchases for the year: {}.", dateYear);
        List<PurchaseDto> purchases = purchaseService.getAllPurchasesForYearRangeFromGivenYear(dateYear);

        if (purchases.isEmpty()) {
            log.info("No purchases found for the current year range.");
        } else {
            log.info("Fetched {} purchases for the current year range.", purchases.size());
        }

        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }

    @GetMapping("/month-range")
    public ResponseEntity<List<PurchaseDto>> getCurrentMonthRangePurchases(@RequestParam(name = "dateTime", required = true) String dateMonth) {
        log.info("Received request to fetch purchases for the year: {}.", dateMonth);
        List<PurchaseDto> purchases = purchaseService.getAllPurchasesForMonthRangeFromGivenMonth(dateMonth);

        if (purchases.isEmpty()) {
            log.info("No purchases found for the current year range.");
        } else {
            log.info("Fetched {} purchases for the current year range.", purchases.size());
        }

        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }
    
}
