package com.am.challenge.service;

import com.am.challenge.dto.ApplicationDto;
import com.am.challenge.dto.CreateApplicationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public ApplicationDto createAndSendApplication(CreateApplicationRequest request) {

        // this is distributed transaction
        // we should use Transaction outbox patter https://microservices.io/patterns/data/transactional-outbox.html for sending messages
        kafkaTemplate.send("topic", "key", "value");
        return null;
    }
}
