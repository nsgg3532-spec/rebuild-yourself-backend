package com.rebuildyourself.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class DiaryEntryResponse {
    private Long id;
    private LocalDate entryDate;
    private String mood;
    private String content;
    private int wordCount;
}