package com.rebuildyourself.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class UpdateReminderRequest {
    private String title;
    private String message;
    private LocalTime reminderTime;
    private boolean active;
}