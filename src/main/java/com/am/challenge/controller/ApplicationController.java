package com.am.challenge.controller;

import com.am.challenge.dto.ApplicationDto;
import com.am.challenge.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ApplicationDto createApplication(@RequestBody @Valid ApplicationDto dto) {
        log.debug("Create application for user: {}", dto.getEmail());
        return applicationService.createAndSendApplication(dto);
    }

    @GetMapping
    List<ApplicationDto> findAllApplications() {
        log.debug("Finding all application");
        return applicationService.findAllApplications();
    }

    @GetMapping(value = "/{applicationId}/reports/pdf", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    ResponseEntity<Resource> generatePdfReport(@PathVariable Long applicationId) {
        log.debug("Generate PDF report for application: {}", applicationId);

        Resource resource = applicationService.generatePdfReport(applicationId);

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
    }

}
