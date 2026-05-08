package com.offerpilot.controller;

import com.offerpilot.dto.CodeSubmitRequest;
import com.offerpilot.dto.CodingSessionRequest;
import com.offerpilot.model.CodingQuestion;
import com.offerpilot.model.InterviewSession;
import com.offerpilot.repository.CodingQuestionRepository;
import com.offerpilot.repository.InterviewSessionRepository;
import com.offerpilot.repository.SessionFeedbackRepository;
import com.offerpilot.model.SessionFeedback;
import com.offerpilot.service.AiProxyService;
import com.offerpilot.security.CustomUserDetails;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final SessionFeedbackRepository feedbackRepository;
    private final AiProxyService aiProxyService;
    private final ObjectMapper objectMapper;

    public InterviewSessionController(InterviewSessionRepository sessionRepository,
            CodingQuestionRepository questionRepository,
            SessionFeedbackRepository feedbackRepository,
            AiProxyService aiProxyService,
            ObjectMapper objectMapper) {
        this.sessionRepository = sessionRepository;
        this.questionRepository = questionRepository;
        this.feedbackRepository = feedbackRepository;
        this.aiProxyService = aiProxyService;
        this.objectMapper = objectMapper;
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

        try {
            String aiResponse = aiProxyService.getCodeFeedback(session.getQuestion().getDescription(),
                    request.getCode());
            JsonNode jsonNode = objectMapper.readTree(aiResponse);

            SessionFeedback feedback = SessionFeedback.builder()
                    .session(session)
                    .overallScore(jsonNode.has("overallScore") ? jsonNode.get("overallScore").asInt() : 0)
                    .strengths(jsonNode.has("strengths") ? jsonNode.get("strengths").asText() : "")
                    .improvements(jsonNode.has("improvements") ? jsonNode.get("improvements").asText() : "")
                    .detailedFeedback(
                            jsonNode.has("detailed_feedback") ? jsonNode.get("detailed_feedback").asText() : "")
                    .build();

            feedbackRepository.save(feedback);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(session);
    }
}
