package com.rebuildyourself.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CreateRoadmapRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotEmpty(message = "At least one step is required")
    private List<String> steps; // order isi list ke order se decide hoga
}