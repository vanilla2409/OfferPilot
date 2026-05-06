package com.offerpilot.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "interview_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "session_type", nullable = false)
    private SessionType sessionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private CodingQuestion question;

    @Column(name = "hr_topic")
    private String hrTopic;

    @Column(name = "user_code", columnDefinition = "TEXT")
    private String userCode;

    @Column(name = "user_answer", columnDefinition = "TEXT")
    private String userAnswer;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.IN_PROGRESS;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @PrePersist
    protected void onCreate() {
        startedAt = LocalDateTime.now();
    }

    public enum SessionType { CODING, HR }
    public enum Status { IN_PROGRESS, COMPLETED, ABANDONED }
}
