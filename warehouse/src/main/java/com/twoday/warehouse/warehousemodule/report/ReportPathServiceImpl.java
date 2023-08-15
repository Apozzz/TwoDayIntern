package com.twoday.warehouse.warehousemodule.report;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.twoday.warehouse.warehousemodule.report.interfaces.ReportPathService;
import com.twoday.warehouse.warehousemodule.report.utils.ReportPathUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportPathServiceImpl implements ReportPathService {

    private final ReportPathUtil pathUtil;

    @Override
    public String getFullPath(Optional<String> dateTime, String filename) {
        return pathUtil.getReportFullPath(filename);
    }

    @Override
    public String getDirectory() {
        return pathUtil.getReportDirectoryPath();
    }

}
