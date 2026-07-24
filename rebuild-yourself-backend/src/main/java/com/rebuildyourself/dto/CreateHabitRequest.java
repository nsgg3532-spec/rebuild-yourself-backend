package com.rebuildyourself.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateHabitRequest {

    @NotBlank(message = "Habit name is required")
    private String name;

    @NotBlank(message = "Category is required")
    private String category; // GYM, MEDITATION, STUDY, READING, WORK, SLEEP, WATER_INTAKE, CUSTOM
}