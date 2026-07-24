package com.rebuildyourself.controller;

import com.rebuildyourself.dto.*;
import com.rebuildyourself.service.HabitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habits")
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;

    @PostMapping
    public ResponseEntity<HabitResponse> createHabit(@Valid @RequestBody CreateHabitRequest request) {
        return ResponseEntity.ok(habitService.createHabit(request));
    }

    @GetMapping
    public ResponseEntity<List<HabitResponse>> getMyHabits() {
        return ResponseEntity.ok(habitService.getMyHabits());
    }

    @PatchMapping("/{habitId}/mark")
    public ResponseEntity<HabitResponse> markHabit(@PathVariable Long habitId,
                                                   @RequestBody MarkHabitRequest request) {
        return ResponseEntity.ok(habitService.markHabit(habitId, request));
    }

    @DeleteMapping("/{habitId}")
    public ResponseEntity<Void> deactivateHabit(@PathVariable Long habitId) {
        habitService.deactivateHabit(habitId);
        return ResponseEntity.noContent().build();
    }
}