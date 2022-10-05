package com.am.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GitHubService {

    private final RestTemplate restTemplate;

    public InputStream getUserAvatar(String username) {
        try {
            String avatarUrl = restTemplate.exchange("https://api.github.com/users/" + username, HttpMethod.GET, HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    })
                .getBody()
                .get("avatar_url")
                .toString();

            return restTemplate.exchange(avatarUrl, HttpMethod.GET, HttpEntity.EMPTY, Resource.class)
                .getBody()
                .getInputStream();
        } catch (Exception e) {
            throw new RuntimeException("Error getting avatar for user: " + username, e);
        }
    }
}
