package com.twoday.wms.warehouse.report.interfaces;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public interface ReportFileService {
    
    void saveToFile(String data);
    File getFile(String dateTime);
    File getLatestFile();
    File determineCorrectFile(String dateTime);
    String getLastGeneratedBasicReportDate();
    LocalDateTime getLastGeneratedStandardReportDate() throws IOException;

}
