package com.offerpilot.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "session_feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false, unique = true)
    private InterviewSession session;

    @Column(name = "overall_score")
    private Integer overallScore;

    @Column(name = "correctness_score")
    private Integer correctnessScore;

    @Column(name = "efficiency_score")
    private Integer efficiencyScore;

    @Column(name = "communication_score")
    private Integer communicationScore;

    @Column(columnDefinition = "TEXT")
    private String strengths;

    @Column(columnDefinition = "TEXT")
    private String improvements;

    @Column(name = "detailed_feedback", columnDefinition = "TEXT")
    private String detailedFeedback;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
