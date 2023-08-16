package com.twoday.wms.warehouse.report;

import org.springframework.stereotype.Service;

import com.twoday.wms.warehouse.interfaces.ReportPathService;
import com.twoday.wms.warehouse.report.utils.ReportPathUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportPathServiceImpl implements ReportPathService {

    private final ReportPathUtil pathUtil;

    @Override
    public String getFullPath(String dateTime, String filename) {
        return pathUtil.getReportFullPath(filename);
    }

    @Override
    public String getDirectory() {
        return pathUtil.getReportDirectoryPath();
    }

}
