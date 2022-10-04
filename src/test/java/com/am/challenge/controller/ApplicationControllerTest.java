package com.am.challenge.controller;

import com.am.challenge.dto.CreateApplicationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
            .bodyValue(CreateApplicationRequest.builder()
                .name("DenisV")
                .build())
            .exchange()
            .expectStatus().isCreated();

    }

}