package com.rebuildyourself.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class RoadmapResponse {
    private Long id;
    private String title;
    private double completionPercentage;
    private List<StepResponse> steps;

    @Data
    @Builder
    @AllArgsConstructor
    public static class StepResponse {
        private Long id;
        private String stepName;
        private int stepOrder;
        private boolean completed;
    }
}