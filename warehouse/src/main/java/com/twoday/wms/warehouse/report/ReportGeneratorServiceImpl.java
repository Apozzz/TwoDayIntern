package com.twoday.wms.warehouse.report;

import static com.twoday.wms.warehouse.report.constants.CsvConstants.NEW_LINE;

import java.util.List;

import org.springframework.stereotype.Service;

import com.twoday.wms.dto.PurchaseDto;
import com.twoday.wms.warehouse.purchase.Purchase;
import com.twoday.wms.warehouse.report.generators.CsvHeaderGenerator;
import com.twoday.wms.warehouse.report.generators.CsvRowGenerator;
import com.twoday.wms.warehouse.report.interfaces.ReportGeneratorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportGeneratorServiceImpl implements ReportGeneratorService {

    private final CsvHeaderGenerator csvHeaderGenerator;
    private final CsvRowGenerator csvRowGenerator;

    @Override
    public String generateCSV(List<PurchaseDto> purchases) {
        StringBuilder csvBuilder = new StringBuilder();

        String header = csvHeaderGenerator.generateCSVHeader(Purchase.class);
        csvBuilder.append(header).append(NEW_LINE);

        for (PurchaseDto purchase : purchases) {
            String row = csvRowGenerator.generateCSVRow(purchase);
            csvBuilder.append(row).append(NEW_LINE);
        }

        return csvBuilder.toString();
    }
    
}
