package com.twoday.wms.warehouse.report.interfaces;

import java.util.Optional;

public interface ReportPathService {
    
    String getFullPath(Optional<String> dateTime, String filename);
    String getDirectory();

}
