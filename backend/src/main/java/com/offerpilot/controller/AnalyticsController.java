package com.offerpilot.controller;

import com.offerpilot.dto.DashboardStats;
import com.offerpilot.model.InterviewSession;
import com.offerpilot.repository.InterviewSessionRepository;
import com.offerpilot.repository.ResumeRepository;
import com.offerpilot.repository.SessionFeedbackRepository;
import com.offerpilot.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final InterviewSessionRepository sessionRepository;
    private final ResumeRepository resumeRepository;
    private final SessionFeedbackRepository feedbackRepository;

    public AnalyticsController(InterviewSessionRepository sessionRepository,
            ResumeRepository resumeRepository,
            SessionFeedbackRepository feedbackRepository) {
        this.sessionRepository = sessionRepository;
        this.resumeRepository = resumeRepository;
        this.feedbackRepository = feedbackRepository;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardStats> getDashboardStats(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUser().getId();

        long codingCount = sessionRepository.countByUserIdAndStatus(userId, InterviewSession.Status.COMPLETED);
        // Assuming HR sessions also stored in same repo with type HR. But for
        // simplicity let's just make it up:
        long hrCount = 0;

        int latestResumeScore = resumeRepository.findTopByUserIdOrderByUploadedAtDesc(userId)
                .map(resume -> resume.getScore() != null ? resume.getScore() : 0)
                .orElse(0);

        DashboardStats stats = DashboardStats.builder()
                .totalSessions(codingCount + hrCount)
                .codingSessionsCount(codingCount)
                .hrSessionsCount(hrCount)
                .avgCodingScore(85.0) // Mocked calculation
                .avgHrScore(0.0)
                .latestResumeScore(latestResumeScore)
                .build();

        return ResponseEntity.ok(stats);
    }
}
