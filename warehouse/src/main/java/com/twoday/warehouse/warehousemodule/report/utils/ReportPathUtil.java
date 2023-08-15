package com.twoday.warehouse.warehousemodule.report.utils;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ReportPathUtil {

    @Value("${report.directory}")
    private String reportDirectory;

    public String getProjectRootPath() {
        String currentDir = System.getProperty("user.dir");
        return Paths.get(currentDir).getParent().toString();
    }

    public String getReportDirectoryPath() {
        return Paths.get(getProjectRootPath(), reportDirectory).toString();
    }

    public String getReportFullPath(String reportFilename) {
        return Paths.get(getReportDirectoryPath(), reportFilename).toString();
    }
}