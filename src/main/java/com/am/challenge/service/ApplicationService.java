package com.am.challenge.service;

import com.am.challenge.domain.Application;
import com.am.challenge.dto.ApplicationDto;
import com.am.challenge.mapper.ApplicationMapper;
import com.am.challenge.repository.ApplicationRepository;
import com.am.challenge.repository.PastProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ApplicationRepository applicationRepository;
    private final PastProjectRepository pastProjectRepository;
    private final DocumentService documentService;
    private final ApplicationMapper mapper;

    @Transactional
    public ApplicationDto createAndSendApplication(ApplicationDto dto) {

        // remove previous application if exists
        applicationRepository
            .findByNameAndEmail(dto.getName(), dto.getEmail())
            .ifPresent(existingApplication -> {
                pastProjectRepository.deleteAllByApplicationId(existingApplication.getId());
                applicationRepository.delete(existingApplication);
                applicationRepository.flush();
            });

        // create new application
        Application application = mapper.toEntity(dto);
        Application savedApplication = applicationRepository.save(application);
        application
            .getProjects()
            .forEach(pastProject -> pastProject.setApplication(savedApplication));
        pastProjectRepository.saveAll(application.getProjects());
        ApplicationDto response = mapper.toDto(savedApplication);

        // this is distributed transaction
        // we should use Transaction outbox patter https://microservices.io/patterns/data/transactional-outbox.html for sending messages
        kafkaTemplate.send("compliance-department-topic", response.getId().toString(), response);
        return response;
    }

    @Transactional(readOnly = true)
    public List<ApplicationDto> findAllApplications() {
        List<Application> allWithProjects = applicationRepository.findWithProjectsAllBy();
        return mapper.toDto(allWithProjects);
    }

    @Transactional(readOnly = true)
    public Resource generatePdfReport(Long applicationId) {
        Application application = applicationRepository
            .findById(applicationId)
            .orElseThrow(() -> new RuntimeException("Application with id: " + applicationId + " is not found"));
        try {
            return documentService.generateApplicationPdf(application).get();
        } catch (Exception e) {
            throw new RuntimeException("Error generating pdf report for application: " + application.getName(), e);
        }
    }
}
