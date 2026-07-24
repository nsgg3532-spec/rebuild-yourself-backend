package com.rebuildyourself.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
public class ReminderResponse {
    private Long id;
    private String title;
    private String message;
    private LocalTime reminderTime;
    private boolean active;
}