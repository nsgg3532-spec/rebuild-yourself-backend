package com.rebuildyourself.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class RewardSummaryResponse {
    private int totalXp;
    private int coins;
    private List<String> earnedBadges;
}