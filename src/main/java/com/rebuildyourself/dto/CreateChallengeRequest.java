package com.rebuildyourself.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateChallengeRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @Min(value = 1, message = "Total days must be at least 1")
    private int totalDays;
}