package com.rebuildyourself.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class HabitResponse {
    private Long id;
    private String name;
    private String category;
    private LocalDate createdDate;
    private boolean active;
    private int currentStreak;
    private boolean completedToday;
}