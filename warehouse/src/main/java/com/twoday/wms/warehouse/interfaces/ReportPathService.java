package com.twoday.wms.warehouse.interfaces;

public interface ReportPathService {
    
    String getFullPath(String dateTime, String filename);
    String getDirectory();

}
