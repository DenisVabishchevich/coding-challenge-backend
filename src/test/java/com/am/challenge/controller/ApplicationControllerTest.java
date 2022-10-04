package com.am.challenge.controller;

import com.am.challenge.domain.Capacity;
import com.am.challenge.domain.EmploymentMode;
import com.am.challenge.dto.ApplicationDto;
import com.am.challenge.dto.PastProjectDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("tetst")
@AutoConfigureWebTestClient(timeout = "PT60S")
class ApplicationControllerTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    void createApplicationTest() {

        client
            .put()
            .uri("/api/v1/applications")
            .bodyValue(ApplicationDto.builder()
                .name("DenisV")
                .eMail("myemail@gmail.com")
                .githubUser("DenisVabishchevich")
                .projects(List.of(PastProjectDto.builder()
                    .capacity(Capacity.FULL_TIME)
                    .duration(Duration.ofDays(35))
                    .employmentMode(EmploymentMode.EMPLOYED)
                    .name("Super Project")
                    .role("Software Developer")
                    .startYear(2022)
                    .teamSize(10)
                    .build()))
                .build())
            .exchange()
            .expectStatus().isCreated();

        Mockito.verify(kafkaTemplate).send("topic", "key", "value");
    }

}