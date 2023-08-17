package com.twoday.wms.warehouse.report;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twoday.wms.warehouse.report.interfaces.ReportFileNameService;
import com.twoday.wms.warehouse.report.interfaces.ReportFileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/reports")
@RequiredArgsConstructor
@Slf4j
public class ReportController {

    static final String CSV_MIME_TYPE = "text/csv";
    static final String CONTENT_DISPOSITION_ATTACHMENT = "attachment; filename=%s";

    private final ReportFileService fileService;
    private final ReportFileNameService fileNameService;

    @GetMapping("/purchases/csv")
    public ResponseEntity<Resource> getPurchasesReport(
            @RequestParam(name = "dateTime", required = false) String dateTime) {
        Resource resource = new FileSystemResource(fileService.determineCorrectFile(dateTime));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        CONTENT_DISPOSITION_ATTACHMENT.formatted(fileNameService.getFileName(dateTime)))
                .contentType(MediaType.parseMediaType(CSV_MIME_TYPE))
                .body(resource);
    }

}
