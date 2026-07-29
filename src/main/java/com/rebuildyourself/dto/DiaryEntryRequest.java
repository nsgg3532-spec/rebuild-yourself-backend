package com.rebuildyourself.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DiaryEntryRequest {

    private LocalDate entryDate; // null diya to aaj ki date use hogi

    @NotBlank(message = "Mood is required")
    private String mood;

    @NotBlank(message = "Content is required")
    private String content;
}