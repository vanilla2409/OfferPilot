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

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeRepository resumeRepository;
    private final AiProxyService aiProxyService;
    private final String uploadDir = "uploads/resumes";

    public ResumeController(ResumeRepository resumeRepository, AiProxyService aiProxyService) {
        this.resumeRepository = resumeRepository;
        this.aiProxyService = aiProxyService;

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

            Resume resume = Resume.builder()
                    .user(userDetails.getUser())
                    .fileName(file.getOriginalFilename())
                    .filePath(filePath.toString())
                    // In a real scenario we parse PDF text. Sending dummy for now.
                    .analysisResult(aiProxyService.analyzeResume("Dummy Text"))
                    .score((int) (Math.random() * 40) + 60)
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
