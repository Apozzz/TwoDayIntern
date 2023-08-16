package com.twoday.wms.warehouse.report;

import java.util.List;

import org.springframework.stereotype.Service;

import com.twoday.wms.warehouse.product.Product;
import com.twoday.wms.warehouse.purchase.Purchase;
import com.twoday.wms.warehouse.report.interfaces.ReportGeneratorService;
import com.twoday.wms.warehouse.user.User;

@Service
public class ReportGeneratorServiceImpl implements ReportGeneratorService {

    private static final String CSV_HEADER = "Purchase ID,User ID,User Name,Product ID,Product Name,Quantity,Time Stamp\n";
    private static final String CSV_SEPARATOR = ",";
    private static final String QUOTE = "\"";
    private static final String DOUBLE_QUOTE = "\"\"";
    private static final String NEW_LINE = "\n";
    private static final String EMPTY_STRING = "";

    @Override
    public String generateCSV(List<Purchase> purchases) {
        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append(CSV_HEADER);

        for (Purchase purchase : purchases) {
            Product product = purchase.getProduct();
            User user = purchase.getUser();

            csvBuilder.append(purchase.getId()).append(CSV_SEPARATOR);
            csvBuilder.append(user.getId()).append(CSV_SEPARATOR);
            csvBuilder.append(user.getUsername()).append(CSV_SEPARATOR);
            csvBuilder.append(product.getId()).append(CSV_SEPARATOR);
            csvBuilder.append(QUOTE).append(escapeCSV(product.getName())).append(QUOTE).append(CSV_SEPARATOR);
            csvBuilder.append(purchase.getQuantity()).append(CSV_SEPARATOR);
            csvBuilder.append(purchase.getTimeStamp()).append(NEW_LINE);
        }

        return csvBuilder.toString();
    }

    private String escapeCSV(String value) {
        if (value == null) {
            return EMPTY_STRING;
        }
        return value.replace(QUOTE, DOUBLE_QUOTE);
    }

}
