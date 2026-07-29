package com.rebuildyourself.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LeaderboardEntryResponse {
    private int rank;
    private String username;
    private int totalXp;
}