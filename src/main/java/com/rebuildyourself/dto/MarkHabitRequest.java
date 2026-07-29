package com.rebuildyourself.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MarkHabitRequest {
    private LocalDate date; // null bhej sakte ho, tab aaj ki date use hogi
    private boolean completed;
}