package com.rebuildyourself.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class ChallengeResponse {
    private Long id;
    private String title;
    private int totalDays;
    private LocalDate startDate;
    private String status;
    private int xpEarned;
    private int daysCompleted;
    private double progressPercentage;
}