package com.twoday.wms.warehouse.purchase;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Service;

import com.twoday.wms.dto.PurchaseDto;
import com.twoday.wms.warehouse.purchase.interfaces.PurchaseRepository;
import com.twoday.wms.warehouse.purchase.interfaces.PurchaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseConverter purchaseConverter;

    @Override
    public List<PurchaseDto> findAllWithTimestampBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return purchaseRepository.findAllWithTimestampBetween(startDate, endDate)
                .stream()
                .map(purchaseConverter::toDto)
                .toList();
    }

    @Override
    public List<PurchaseDto> getAllPurchasesForYearRangeFromGivenYear(Integer yearDate) {
        LocalDateTime startOfRange = LocalDateTime.of(yearDate, Month.JANUARY, 1, 0, 0);
        LocalDateTime endOfRange = LocalDateTime.of(yearDate, Month.DECEMBER, 31, 23, 59, 59);
        return purchaseRepository.findAllWithTimestampBetween(startOfRange, endOfRange)
                .stream()
                .map(purchaseConverter::toDto)
                .toList();
    }

    @Override
    public List<PurchaseDto> getAllPurchasesForMonthRangeFromGivenMonth(String monthDate) {
        String[] parts = monthDate.split("-");
        int year = Integer.parseInt(parts[0]);
        int monthValue = Integer.parseInt(parts[1]);
        Month month = Month.of(monthValue);
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDateTime startOfRange = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endOfRange = LocalDateTime.of(year, month, daysInMonth, 23, 59, 59);

        return purchaseRepository.findAllWithTimestampBetween(startOfRange, endOfRange)
                .stream()
                .map(purchaseConverter::toDto)
                .toList();
    }

}
