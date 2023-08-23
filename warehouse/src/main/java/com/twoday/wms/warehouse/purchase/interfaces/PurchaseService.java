package com.twoday.wms.warehouse.purchase.interfaces;

import java.time.LocalDateTime;
import java.util.List;

import com.twoday.wms.dto.PurchaseDto;

public interface PurchaseService {
    
    List<PurchaseDto> findAllWithTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<PurchaseDto> getAllPurchasesForYearRangeFromGivenYear(Integer yearDate);

}
