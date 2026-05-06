package com.offerpilot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
        // Here we'd map to FastAPI /api/ai/resume
        // For simplicity returning a mock
        return "{\"score\": 85, \"feedback\": \"Good resume, add more metrics.\"}";
    }

    public String getCodeFeedback(String question, String code) {
        // Calls the python service for code feedback
        return "{\"overallScore\": 90, \"strengths\": \"Clean code\", \"improvements\": \"O(n) time is possible.\"}";
    }
}
