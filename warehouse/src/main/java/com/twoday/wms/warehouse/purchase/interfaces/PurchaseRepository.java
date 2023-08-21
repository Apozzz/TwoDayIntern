package com.twoday.wms.warehouse.purchase.interfaces;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.twoday.wms.warehouse.interfaces.GenericRepository;
import com.twoday.wms.warehouse.purchase.Purchase;

public interface PurchaseRepository extends GenericRepository<Purchase, Long> {

    Purchase save(Purchase purchase);

    @Query("select p from Purchase p JOIN FETCH p.user JOIN FETCH p.product where p.timeStamp  between :startDate and :endDate")
    List<Purchase> findAllWithTimestampBetween(@Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

}
