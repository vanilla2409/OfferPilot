package com.offerpilot.controller;

import com.offerpilot.dto.CodeSubmitRequest;
import com.offerpilot.dto.CodingSessionRequest;
import com.offerpilot.model.CodingQuestion;
import com.offerpilot.model.InterviewSession;
import com.offerpilot.repository.CodingQuestionRepository;
import com.offerpilot.repository.InterviewSessionRepository;
import com.offerpilot.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class InterviewSessionController {

    private final InterviewSessionRepository sessionRepository;
    private final CodingQuestionRepository questionRepository;

    public InterviewSessionController(InterviewSessionRepository sessionRepository,
            CodingQuestionRepository questionRepository) {
        this.sessionRepository = sessionRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping
    public ResponseEntity<List<InterviewSession>> getUserSessions(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(sessionRepository.findByUserIdOrderByStartedAtDesc(userDetails.getUser().getId()));
    }

    @PostMapping("/coding")
    public ResponseEntity<?> startCodingSession(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CodingSessionRequest request) {

        CodingQuestion question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        InterviewSession session = InterviewSession.builder()
                .user(userDetails.getUser())
                .sessionType(InterviewSession.SessionType.CODING)
                .question(question)
                .status(InterviewSession.Status.IN_PROGRESS)
                .build();

        sessionRepository.save(session);
        return ResponseEntity.ok(session);
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<?> submitCode(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CodeSubmitRequest request) {

        InterviewSession session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        if (!session.getUser().getId().equals(userDetails.getUser().getId())) {
            return ResponseEntity.status(403).body("Unauthorized to access this session");
        }

        session.setUserCode(request.getCode());
        session.setStatus(InterviewSession.Status.COMPLETED);
        session.setCompletedAt(LocalDateTime.now());

        sessionRepository.save(session);

        // In a real app, here we would call the AI Evaluation Service.
        // For now, we will return the saved session.
        return ResponseEntity.ok(session);
    }
}
