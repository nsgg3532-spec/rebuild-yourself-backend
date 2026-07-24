package com.rebuildyourself.controller;

import com.rebuildyourself.dto.*;
import com.rebuildyourself.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
public class RewardController {

    private final RewardService rewardService;

    @GetMapping("/me")
    public ResponseEntity<RewardSummaryResponse> getMySummary() {
        return ResponseEntity.ok(rewardService.getMySummary());
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<LeaderboardEntryResponse>> getLeaderboard(
            @RequestParam(defaultValue = "10") int topN) {
        return ResponseEntity.ok(rewardService.getLeaderboard(topN));
    }
}