package com.rebuildyourself.service;

import com.rebuildyourself.dto.DashboardResponse;
import com.rebuildyourself.entity.*;
import com.rebuildyourself.repository.*;
import com.rebuildyourself.security.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final CurrentUserProvider currentUserProvider;
    private final HabitRepository habitRepository;
    private final HabitLogRepository habitLogRepository;
    private final ChallengeRepository challengeRepository;
    private final UserBadgeRepository userBadgeRepository;

    private static final List<String> QUOTES = List.of(
            "Discipline is choosing between what you want now and what you want most.",
            "Small steps every day lead to big results.",
            "You don't have to be great to start, but you have to start to be great.",
            "Consistency beats motivation.",
            "The best time to start was yesterday. The next best time is now."
    );

    public DashboardResponse getDashboard() {
        User user = currentUserProvider.getCurrentUser();

        List<DashboardResponse.TodayHabitItem> todayHabits = habitRepository
                .findByUserAndActiveTrue(user)
                .stream()
                .map(this::toTodayHabitItem)
                .toList();

        List<DashboardResponse.ActiveChallengeItem> activeChallenges = challengeRepository
                .findByUser(user)
                .stream()
                .filter(c -> c.getStatus().equals("ACTIVE"))
                .map(this::toActiveChallengeItem)
                .toList();

        int badgesEarned = userBadgeRepository.findByUser(user).size();

        String quote = QUOTES.get(new Random().nextInt(QUOTES.size()));

        return DashboardResponse.builder()
                .username(user.getUsername())
                .totalXp(user.getTotalXp())
                .coins(user.getCoins())
                .badgesEarned(badgesEarned)
                .motivationQuote(quote)
                .todayHabits(todayHabits)
                .activeChallenges(activeChallenges)
                .build();
    }

    private DashboardResponse.TodayHabitItem toTodayHabitItem(Habit habit) {
        boolean completedToday = habitLogRepository.findByHabitAndDate(habit, LocalDate.now())
                .map(HabitLog::isCompleted)
                .orElse(false);

        int streak = calculateStreak(habit);

        return DashboardResponse.TodayHabitItem.builder()
                .habitId(habit.getId())
                .name(habit.getName())
                .category(habit.getCategory())
                .completedToday(completedToday)
                .currentStreak(streak)
                .build();
    }

    private int calculateStreak(Habit habit) {
        List<HabitLog> logs = habitLogRepository.findByHabitOrderByDateDesc(habit);

        int streak = 0;
        LocalDate expectedDate = LocalDate.now();

        for (HabitLog log : logs) {
            if (log.getDate().equals(expectedDate) && log.isCompleted()) {
                streak++;
                expectedDate = expectedDate.minusDays(1);
            } else if (log.getDate().equals(expectedDate)) {
                break;
            }
        }

        return streak;
    }

    private DashboardResponse.ActiveChallengeItem toActiveChallengeItem(Challenge challenge) {
        long completedDays = challenge.getDays().stream()
                .filter(ChallengeDay::isCompleted)
                .count();

        double progress = challenge.getTotalDays() == 0
                ? 0
                : (completedDays * 100.0) / challenge.getTotalDays();

        return DashboardResponse.ActiveChallengeItem.builder()
                .challengeId(challenge.getId())
                .title(challenge.getTitle())
                .daysCompleted((int) completedDays)
                .totalDays(challenge.getTotalDays())
                .progressPercentage(progress)
                .build();
    }
}