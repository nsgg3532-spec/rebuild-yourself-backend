package com.rebuildyourself.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class DashboardResponse {

    private String username;
    private int totalXp;
    private int coins;
    private int badgesEarned;
    private String motivationQuote;

    private List<TodayHabitItem> todayHabits;
    private List<ActiveChallengeItem> activeChallenges;

    @Data
    @Builder
    @AllArgsConstructor
    public static class TodayHabitItem {
        private Long habitId;
        private String name;
        private String category;
        private boolean completedToday;
        private int currentStreak;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class ActiveChallengeItem {
        private Long challengeId;
        private String title;
        private int daysCompleted;
        private int totalDays;
        private double progressPercentage;
    }
}