package com.am.challenge.controller;

import com.am.challenge.dto.ApplicationDto;
import com.am.challenge.dto.CreateApplicationRequest;
import com.am.challenge.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping
    ApplicationDto createApplication(@RequestBody @Valid CreateApplicationRequest request) {
        log.debug("Create application for user: {}", request.getGithubUser());
        return applicationService.createAndSendApplication(request);
    }
}
