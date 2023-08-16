package com.twoday.wms.warehouse.report.interfaces;

import java.io.File;
import java.util.Optional;

public interface ReportFileService {
    
    void saveToFile(String data);
    File getFile(Optional<String> dateTime);
    File getLatestFile();
    File determineCorrectFile(Optional<String> dateTime);
    String getLastGeneratedReportDate();

}
