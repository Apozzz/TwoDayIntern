package com.twoday.wms.warehouse.purchase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public List<PurchaseDto> getAllPurchasesForYearRangeFromCurrentMonth() {
        LocalDateTime startOfRange = LocalDateTime.of(LocalDate.now().minusYears(1).withDayOfMonth(1), LocalTime.MIN);
        LocalDateTime endOfRange = LocalDateTime.now();
        return purchaseRepository.findAllWithTimestampBetween(startOfRange, endOfRange)
                .stream()
                .map(purchaseConverter::toDto)
                .toList();
    }

}
