package com.twoday.wms.warehouse.report.interfaces;

import java.util.Optional;

public interface ReportFileNameService {
    
    String getFileName(Optional<String> dateTime);

}
