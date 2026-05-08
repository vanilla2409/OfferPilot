package com.offerpilot.controller;

import com.offerpilot.model.Resume;
import com.offerpilot.repository.ResumeRepository;
import com.offerpilot.security.CustomUserDetails;
import com.offerpilot.service.AiProxyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeRepository resumeRepository;
    private final AiProxyService aiProxyService;
    private final ObjectMapper objectMapper;
    private final String uploadDir = "uploads/resumes";

    public ResumeController(ResumeRepository resumeRepository, AiProxyService aiProxyService,
            ObjectMapper objectMapper) {
        this.resumeRepository = resumeRepository;
        this.aiProxyService = aiProxyService;
        this.objectMapper = objectMapper;

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadResume(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.copy(file.getInputStream(), filePath);

            String aiResponse = aiProxyService.analyzeResume("Extracted text would go here");
            int score = 60;
            try {
                JsonNode jsonNode = objectMapper.readTree(aiResponse);
                if (jsonNode.has("score")) {
                    score = jsonNode.get("score").asInt();
                }
            } catch (Exception ex) {
                // Ignore parse errors, fallback to 60
            }

            Resume resume = Resume.builder()
                    .user(userDetails.getUser())
                    .fileName(file.getOriginalFilename())
                    .filePath(filePath.toString())
                    .analysisResult(aiResponse)
                    .score(score)
                    .build();

            resumeRepository.save(resume);

            return ResponseEntity.ok(resume);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to upload file");
        }
    }

    @GetMapping
    public ResponseEntity<List<Resume>> getUserResumes(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(resumeRepository.findByUserIdOrderByUploadedAtDesc(userDetails.getUser().getId()));
    }
}
