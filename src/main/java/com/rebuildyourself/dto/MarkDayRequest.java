package com.rebuildyourself.dto;

import lombok.Data;

@Data
public class MarkDayRequest {
    private int dayNumber;
    private boolean completed; // true = tick, false = cross
}