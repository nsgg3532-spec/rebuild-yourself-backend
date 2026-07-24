package com.rebuildyourself.controller;

import com.rebuildyourself.dto.*;
import com.rebuildyourself.service.RoadmapService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roadmaps")
@RequiredArgsConstructor
public class RoadmapController {

    private final RoadmapService roadmapService;

    @PostMapping
    public ResponseEntity<RoadmapResponse> createRoadmap(@Valid @RequestBody CreateRoadmapRequest request) {
        return ResponseEntity.ok(roadmapService.createRoadmap(request));
    }

    @GetMapping
    public ResponseEntity<List<RoadmapResponse>> getMyRoadmaps() {
        return ResponseEntity.ok(roadmapService.getMyRoadmaps());
    }

    @PatchMapping("/{roadmapId}/steps/{stepId}")
    public ResponseEntity<RoadmapResponse> updateStep(@PathVariable Long roadmapId,
                                                      @PathVariable Long stepId,
                                                      @RequestBody UpdateStepRequest request) {
        return ResponseEntity.ok(roadmapService.updateStep(roadmapId, stepId, request));
    }

    @DeleteMapping("/{roadmapId}")
    public ResponseEntity<Void> deleteRoadmap(@PathVariable Long roadmapId) {
        roadmapService.deleteRoadmap(roadmapId);
        return ResponseEntity.noContent().build();
    }
}