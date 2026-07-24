package com.rebuildyourself.controller;

import com.rebuildyourself.dto.*;
import com.rebuildyourself.service.ChallengeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping
    public ResponseEntity<ChallengeResponse> createChallenge(@Valid @RequestBody CreateChallengeRequest request) {
        return ResponseEntity.ok(challengeService.createChallenge(request));
    }

    @GetMapping
    public ResponseEntity<List<ChallengeResponse>> getMyChallenges() {
        return ResponseEntity.ok(challengeService.getMyChallenges());
    }

    @PatchMapping("/{challengeId}/mark-day")
    public ResponseEntity<ChallengeResponse> markDay(@PathVariable Long challengeId,
                                                     @RequestBody MarkDayRequest request) {
        return ResponseEntity.ok(challengeService.markDay(challengeId, request));
    }
}