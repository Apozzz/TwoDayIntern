package com.twoday.wms.shop.report;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ReportService {

    private static final String CSV_MIME_TYPE = "text/csv";
    private static final String CONTENT_DISPOSITION_ATTACHMENT = "attachment; filename=%s";
    private static final String DATE_TIME_QUERY_PARAM = "dateTime";
    private static final String FILENAME_KEY_IN_HEADER = "filename=";
    private static final String DEFAULT_FILENAME = "report.csv";
    private static final int FILENAME_POSITION = 1;
    private static final String QUOTE_REMOVAL_REGEX = "\"";
    private static final String EMPTY_STRING = "";

    private final WebClient webClient;

    public Mono<ResponseEntity<ByteArrayResource>> getPurchaseReport(String dateTime) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/reports/purchases/csv")
                        .queryParam(DATE_TIME_QUERY_PARAM, dateTime)
                        .build())
                .exchangeToMono(clientResponse -> {
                    Mono<ByteArrayResource> body = clientResponse.bodyToMono(ByteArrayResource.class);
                    String contentDisposition = clientResponse.headers()
                            .header(HttpHeaders.CONTENT_DISPOSITION)
                            .stream()
                            .findFirst()
                            .orElse(EMPTY_STRING);
                    String filename = extractFilename(contentDisposition);
                    return body.map(resource -> ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION,
                                    String.format(CONTENT_DISPOSITION_ATTACHMENT, filename))
                            .contentType(MediaType.parseMediaType(CSV_MIME_TYPE))
                            .body(resource));
                });
    }

    private String extractFilename(String contentDisposition) {
        if (contentDisposition == null || !contentDisposition.contains(FILENAME_KEY_IN_HEADER)) {
            return DEFAULT_FILENAME;
        }

        return contentDisposition.split(FILENAME_KEY_IN_HEADER)[FILENAME_POSITION]
                .replace(QUOTE_REMOVAL_REGEX, EMPTY_STRING).trim();
    }

}
