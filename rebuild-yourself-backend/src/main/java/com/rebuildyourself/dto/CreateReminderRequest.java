package com.rebuildyourself.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class CreateReminderRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Message is required")
    private String message;

    @NotNull(message = "Reminder time is required")
    private LocalTime reminderTime;
}