package com.offerpilot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AiProxyService {

    private final RestTemplate restTemplate;

    @Value("${ai.service.url}")
    private String aiServiceUrl;

    public AiProxyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String analyzeResume(String textContent) {
        String url = aiServiceUrl + "/api/ai/resume";
        Map<String, String> request = new HashMap<>();
        request.put("text", textContent);

        try {
            return restTemplate.postForObject(url, request, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"score\": 0, \"feedback\": \"Failed to reach AI Service.\"}";
        }
    }

    public String getCodeFeedback(String questionDescription, String code) {
        String url = aiServiceUrl + "/api/ai/code-feedback";
        Map<String, String> request = new HashMap<>();
        request.put("question_description", questionDescription);
        request.put("code", code);

        try {
            return restTemplate.postForObject(url, request, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"overallScore\": 0, \"detailed_feedback\": \"Failed to reach AI Service.\"}";
        }
    }
}
