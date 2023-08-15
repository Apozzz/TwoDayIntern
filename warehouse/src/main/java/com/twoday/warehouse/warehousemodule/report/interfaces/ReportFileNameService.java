package com.twoday.warehouse.warehousemodule.report.interfaces;

import java.util.Optional;

public interface ReportFileNameService {
    
    String getFileName(Optional<String> dateTime);

}
