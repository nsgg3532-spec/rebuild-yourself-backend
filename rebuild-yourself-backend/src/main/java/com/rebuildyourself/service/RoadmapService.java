package com.rebuildyourself.service;

import com.rebuildyourself.dto.*;
import com.rebuildyourself.entity.*;
import com.rebuildyourself.repository.RoadmapRepository;
import com.rebuildyourself.repository.RoadmapStepRepository;
import com.rebuildyourself.security.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoadmapService {

    private final RoadmapRepository roadmapRepository;
    private final RoadmapStepRepository roadmapStepRepository;
    private final CurrentUserProvider currentUserProvider;

    public RoadmapResponse createRoadmap(CreateRoadmapRequest request) {
        User user = currentUserProvider.getCurrentUser();

        Roadmap roadmap = Roadmap.builder()
                .user(user)
                .title(request.getTitle())
                .build();

        roadmapRepository.save(roadmap);

        int order = 1;
        for (String stepName : request.getSteps()) {
            RoadmapStep step = RoadmapStep.builder()
                    .roadmap(roadmap)
                    .stepName(stepName)
                    .stepOrder(order++)
                    .completed(false)
                    .build();
            roadmap.getSteps().add(step);
        }

        roadmapRepository.save(roadmap);

        return toResponse(roadmap);
    }

    public List<RoadmapResponse> getMyRoadmaps() {
        User user = currentUserProvider.getCurrentUser();
        return roadmapRepository.findByUser(user)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public RoadmapResponse updateStep(Long roadmapId, Long stepId, UpdateStepRequest request) {
        User user = currentUserProvider.getCurrentUser();

        Roadmap roadmap = roadmapRepository.findByIdAndUser(roadmapId, user)
                .orElseThrow(() -> new RuntimeException("Roadmap not found"));

        RoadmapStep step = roadmap.getSteps().stream()
                .filter(s -> s.getId().equals(stepId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Step not found in this roadmap"));

        step.setCompleted(request.isCompleted());
        roadmapStepRepository.save(step);

        return toResponse(roadmap);
    }

    public void deleteRoadmap(Long roadmapId) {
        User user = currentUserProvider.getCurrentUser();
        Roadmap roadmap = roadmapRepository.findByIdAndUser(roadmapId, user)
                .orElseThrow(() -> new RuntimeException("Roadmap not found"));
        roadmapRepository.delete(roadmap);
    }

    private RoadmapResponse toResponse(Roadmap roadmap) {
        List<RoadmapResponse.StepResponse> stepResponses = roadmap.getSteps().stream()
                .sorted((a, b) -> Integer.compare(a.getStepOrder(), b.getStepOrder()))
                .map(s -> RoadmapResponse.StepResponse.builder()
                        .id(s.getId())
                        .stepName(s.getStepName())
                        .stepOrder(s.getStepOrder())
                        .completed(s.isCompleted())
                        .build())
                .toList();

        long completedCount = roadmap.getSteps().stream().filter(RoadmapStep::isCompleted).count();
        double percentage = roadmap.getSteps().isEmpty()
                ? 0
                : (completedCount * 100.0) / roadmap.getSteps().size();

        return RoadmapResponse.builder()
                .id(roadmap.getId())
                .title(roadmap.getTitle())
                .completionPercentage(percentage)
                .steps(stepResponses)
                .build();
    }
}