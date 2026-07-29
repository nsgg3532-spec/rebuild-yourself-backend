package com.rebuildyourself.service;

import com.rebuildyourself.dto.*;
import com.rebuildyourself.entity.*;
import com.rebuildyourself.repository.BadgeRepository;
import com.rebuildyourself.repository.UserBadgeRepository;
import com.rebuildyourself.repository.UserRepository;
import com.rebuildyourself.security.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RewardService {

    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;
    private final UserBadgeRepository userBadgeRepository;
    private final CurrentUserProvider currentUserProvider;

    // Challenge/Habit module se ye method call hoga jab kuch complete ho
    public void addXpAndCoins(User user, int xp, int coins) {
        user.setTotalXp(user.getTotalXp() + xp);
        user.setCoins(user.getCoins() + coins);
        userRepository.save(user);

        checkAndUnlockBadges(user);
    }

    private void checkAndUnlockBadges(User user) {
        List<Badge> eligibleBadges = badgeRepository.findByXpRequiredLessThanEqual(user.getTotalXp());

        for (Badge badge : eligibleBadges) {
            if (!userBadgeRepository.existsByUserAndBadgeId(user, badge.getId())) {
                UserBadge userBadge = UserBadge.builder()
                        .user(user)
                        .badge(badge)
                        .earnedDate(LocalDate.now())
                        .build();
                userBadgeRepository.save(userBadge);
            }
        }
    }

    public RewardSummaryResponse getMySummary() {
        User user = currentUserProvider.getCurrentUser();

        List<String> badgeNames = userBadgeRepository.findByUser(user)
                .stream()
                .map(ub -> ub.getBadge().getName())
                .toList();

        return RewardSummaryResponse.builder()
                .totalXp(user.getTotalXp())
                .coins(user.getCoins())
                .earnedBadges(badgeNames)
                .build();
    }

    public List<LeaderboardEntryResponse> getLeaderboard(int topN) {
        List<User> topUsers = userRepository.findAllByOrderByTotalXpDesc(PageRequest.of(0, topN));

        int[] rank = {1};
        return topUsers.stream()
                .map(u -> LeaderboardEntryResponse.builder()
                        .rank(rank[0]++)
                        .username(u.getUsername())
                        .totalXp(u.getTotalXp())
                        .build())
                .toList();
    }
}