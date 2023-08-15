package com.twoday.warehouse.warehousemodule.report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.twoday.warehouse.warehousemodule.report.exceptions.InvalidDateTimeFormatException;
import com.twoday.warehouse.warehousemodule.report.exceptions.ReportFileException;
import com.twoday.warehouse.warehousemodule.report.interfaces.ReportFileNameService;
import com.twoday.warehouse.warehousemodule.report.interfaces.ReportFileService;
import com.twoday.warehouse.warehousemodule.report.interfaces.ReportPathService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportFileServiceImpl implements ReportFileService {

    private static final String ERROR_WRITING_TO_FILE = "Error writing to file";
    private static final String NO_REPORTS_FOUND = "No reports found in the directory";
    private static final String FILE_DOES_NOT_EXIST = "The requested report file does not exist.";
    private static final String UNABLE_TO_EXTRACT_DATE = "Unable to extract date from the report filename.";
    private static final String CSV_EXTENSION = ".csv";
    private static final String FILENAME_SEPARATOR = "-";
    private static final String EMPTY_STRING = "";
    private static final int SPLIT_LIMIT = 2;
    private static final int SECOND_PART_INDEX = 1;

    private final ReportPathService pathService;
    private final ReportFileNameService fileNameService;

    @Override
    public void saveToFile(String data) {
        File file = new File(pathService.getFullPath(Optional.empty(), fileNameService.getFileName(Optional.empty())));

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(data);
        } catch (IOException e) {
            throw new ReportFileException(ERROR_WRITING_TO_FILE);
        }
    }

    @Override
    public File getFile(Optional<String> dateTime) {
        return new File(pathService.getFullPath(dateTime, fileNameService.getFileName(dateTime)));
    }

    @Override
    public File getLatestFile() {
        File reportDirectory = new File(pathService.getDirectory());
        File[] files = reportDirectory.listFiles();

        return Arrays.stream(files)
                .max(Comparator.comparingLong(File::lastModified))
                .orElseThrow(() -> new ReportFileException(NO_REPORTS_FOUND));
    }

    @Override
    public File determineCorrectFile(Optional<String> dateTime) {
        try {
            File file = dateTime.isPresent() ? getFile(dateTime) : getLatestFile();

            if (!file.exists()) {
                throw new ReportFileException(FILE_DOES_NOT_EXIST);
            }

            return file;
        } catch (RuntimeException e) {
            throw new InvalidDateTimeFormatException("%s Consider using the last generated report date: %s"
                    .formatted(e.getMessage(), getLastGeneratedReportDate()));
        }
    }

    @Override
    public String getLastGeneratedReportDate() {
        File latestReport = getLatestFile();
        String fileName = latestReport.getName();
        String[] parts = fileName.split(FILENAME_SEPARATOR, SPLIT_LIMIT);

        if (parts.length > SECOND_PART_INDEX) {
            return parts[SECOND_PART_INDEX].replace(CSV_EXTENSION, EMPTY_STRING);
        }
        throw new ReportFileException(UNABLE_TO_EXTRACT_DATE);
    }

}
