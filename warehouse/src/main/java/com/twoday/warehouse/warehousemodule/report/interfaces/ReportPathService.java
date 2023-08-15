package com.twoday.warehouse.warehousemodule.report.interfaces;

import java.util.Optional;

public interface ReportPathService {
    
    String getFullPath(Optional<String> dateTime, String filename);
    String getDirectory();

}
