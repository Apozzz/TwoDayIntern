package com.twoday.wms.warehouse.report.interfaces;

import java.io.File;

public interface ReportFileService {
    
    void saveToFile(String data);
    File getFile(String dateTime);
    File getLatestFile();
    File determineCorrectFile(String dateTime);
    String getLastGeneratedReportDate();

}
