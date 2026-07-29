package com.rebuildyourself.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class StatisticsResponse {

    private List<DailyCompletion> last7DaysHabitCompletion;

    private int totalChallenges;
    private int activeChallenges;
    private int completedChallenges;
    private int failedChallenges;

    private int totalActiveHabits;
    private double overallHabitCompletionRate; // last 7 days ke hisaab se

    @Data
    @Builder
    @AllArgsConstructor
    public static class DailyCompletion {
        private LocalDate date;
        private int completedCount;
        private int totalActiveHabits;
        private double completionPercentage;
    }
}