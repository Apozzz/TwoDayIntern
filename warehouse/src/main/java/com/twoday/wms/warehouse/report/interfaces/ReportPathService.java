package com.twoday.wms.warehouse.report.interfaces;

public interface ReportPathService {
    
    String getFullPath(String dateTime, String filename);
    String getDirectory();

}
