package com.am.challenge.controller;

import com.am.challenge.domain.Capacity;
import com.am.challenge.domain.EmploymentMode;
import com.am.challenge.dto.ApplicationDto;
import com.am.challenge.dto.PastProjectDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient(timeout = "PT60S")
class ApplicationControllerTest {

    @Autowired
    private WebTestClient client;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    void createFindApplicationsTest() throws Exception {

        client
            .post()
            .uri("/api/v1/applications")
            .bodyValue(ApplicationDto.builder()
                .name("DenisV")
                .email("myemail@gmail.com")
                .githubUser("DenisVabishchevich")
                .projects(List.of(PastProjectDto.builder()
                    .capacity(Capacity.FULL_TIME)
                    .duration(Duration.ofDays(35))
                    .employmentMode(EmploymentMode.EMPLOYED)
                    .name("Super Project")
                    .role("Software Developer")
                    .startYear(2022)
                    .teamSize(10)
                    .lifeUrl("www.life.com")
                    .repositoryUrl("www.repo-rul.com")
                    .build()))
                .build())
            .exchange()
            .expectStatus().isCreated()
            .expectBody()
            .consumeWith(System.out::println)
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.projects[*].id").value(hasItems(CoreMatchers.notNullValue()));

        byte[] createBody = client
            .post()
            .uri("/api/v1/applications")
            .bodyValue(ApplicationDto.builder()
                .name("DenisV")
                .email("myemail@gmail.com")
                .githubUser("DenisVabishchevich")
                .projects(List.of(PastProjectDto.builder()
                    .capacity(Capacity.FULL_TIME)
                    .duration(Duration.ofDays(35))
                    .employmentMode(EmploymentMode.EMPLOYED)
                    .name("Super Project")
                    .role("Software Developer")
                    .startYear(2022)
                    .teamSize(10)
                    .lifeUrl("www.life.com")
                    .repositoryUrl("www.repo-rul.com")
                    .build()))
                .build())
            .exchange()
            .expectStatus().isCreated()
            .expectBody()
            .consumeWith(System.out::println)
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.projects[*].id").value(hasItems(CoreMatchers.notNullValue()))
            .returnResult()
            .getResponseBody();

        client
            .get()
            .uri("/api/v1/applications")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .consumeWith(System.out::println)
            .jsonPath("$.[*].name").value(hasItems("DenisV"))
            .jsonPath("$.[*].projects[*].name").value(hasItems("Super Project"));


        ApplicationDto created = mapper.readValue(createBody, ApplicationDto.class);

        byte[] pdfReport = client
            .get()
            .uri("/api/v1/applications/{applicationId}/reports/pdf", created.getId())
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .consumeWith(System.out::println)
            .returnResult()
            .getResponseBody();

        // check valid pdf
        Files.write(Path.of("/tmp/file_pdf.pdf"), pdfReport);

        Mockito.verify(kafkaTemplate, Mockito.times(2))
            .send(Mockito.eq("compliance-department-topic"), Mockito.anyString(), Mockito.any(ApplicationDto.class));
    }

}