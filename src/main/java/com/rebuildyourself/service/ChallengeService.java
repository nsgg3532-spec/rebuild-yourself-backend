package com.rebuildyourself.service;

import com.rebuildyourself.dto.*;
import com.rebuildyourself.entity.*;
import com.rebuildyourself.repository.ChallengeDayRepository;
import com.rebuildyourself.repository.ChallengeRepository;
import com.rebuildyourself.security.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeDayRepository challengeDayRepository;
    private final CurrentUserProvider currentUserProvider;
    private final RewardService rewardService;

    public ChallengeResponse createChallenge(CreateChallengeRequest request) {
        User user = currentUserProvider.getCurrentUser();

        Challenge challenge = Challenge.builder()
                .user(user)
                .title(request.getTitle())
                .totalDays(request.getTotalDays())
                .startDate(LocalDate.now())
                .status("ACTIVE")
                .xpEarned(0)
                .build();

        challengeRepository.save(challenge);

        return toResponse(challenge);
    }

    public List<ChallengeResponse> getMyChallenges() {
        User user = currentUserProvider.getCurrentUser();
        return challengeRepository.findByUser(user)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ChallengeResponse markDay(Long challengeId, MarkDayRequest request) {
        User user = currentUserProvider.getCurrentUser();

        Challenge challenge = challengeRepository.findByIdAndUser(challengeId, user)
                .orElseThrow(() -> new RuntimeException("Challenge not found"));

        if (request.getDayNumber() < 1 || request.getDayNumber() > challenge.getTotalDays()) {
            throw new RuntimeException("Invalid day number");
        }

        ChallengeDay day = challengeDayRepository
                .findByChallengeAndDayNumber(challenge, request.getDayNumber())
                .orElse(ChallengeDay.builder()
                        .challenge(challenge)
                        .dayNumber(request.getDayNumber())
                        .date(LocalDate.now())
                        .build());

        day.setCompleted(request.isCompleted());
        challengeDayRepository.save(day);

        checkAndCompleteChallenge(challenge);

        return toResponse(challenge);
    }

    private void checkAndCompleteChallenge(Challenge challenge) {
        long completedDays = challenge.getDays().stream()
                .filter(ChallengeDay::isCompleted)
                .count();

        if (completedDays >= challenge.getTotalDays() && !challenge.getStatus().equals("COMPLETED")) {
            int xp = challenge.getTotalDays() * 10;
            int coins = challenge.getTotalDays() * 2;

            challenge.setStatus("COMPLETED");
            challenge.setXpEarned(xp);
            challengeRepository.save(challenge);

            rewardService.addXpAndCoins(challenge.getUser(), xp, coins);
        }
    }

    private ChallengeResponse toResponse(Challenge challenge) {
        long completedDays = challenge.getDays().stream()
                .filter(ChallengeDay::isCompleted)
                .count();

        double progress = challenge.getTotalDays() == 0
                ? 0
                : (completedDays * 100.0) / challenge.getTotalDays();

        return ChallengeResponse.builder()
                .id(challenge.getId())
                .title(challenge.getTitle())
                .totalDays(challenge.getTotalDays())
                .startDate(challenge.getStartDate())
                .status(challenge.getStatus())
                .xpEarned(challenge.getXpEarned())
                .daysCompleted((int) completedDays)
                .progressPercentage(progress)
                .build();
    }
}