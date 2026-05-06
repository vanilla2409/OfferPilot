package com.offerpilot.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "dsa_progress")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DsaProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 100)
    private String topic;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.NOT_STARTED;

    @Builder.Default
    private Integer score = 0;

    public enum Status { NOT_STARTED, IN_PROGRESS, COMPLETED }
}
