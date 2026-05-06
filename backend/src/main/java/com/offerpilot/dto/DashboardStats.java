package com.offerpilot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DashboardStats {
    private long totalSessions;
    private long codingSessionsCount;
    private long hrSessionsCount;
    private double avgCodingScore;
    private double avgHrScore;
    private int latestResumeScore;
}
