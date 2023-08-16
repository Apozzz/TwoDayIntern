package com.twoday.wms.warehouse.report;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.twoday.wms.warehouse.report.interfaces.ReportPathService;
import com.twoday.wms.warehouse.report.utils.ReportPathUtil;

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
