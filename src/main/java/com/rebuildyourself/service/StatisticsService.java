package com.rebuildyourself.service;

import com.rebuildyourself.dto.StatisticsResponse;
import com.rebuildyourself.entity.*;
import com.rebuildyourself.repository.*;
import com.rebuildyourself.security.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final CurrentUserProvider currentUserProvider;
    private final HabitRepository habitRepository;
    private final HabitLogRepository habitLogRepository;
    private final ChallengeRepository challengeRepository;

    public StatisticsResponse getStatistics() {
        User user = currentUserProvider.getCurrentUser();

        int totalActiveHabits = habitRepository.findByUserAndActiveTrue(user).size();

        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(6);

        List<HabitLog> logsInRange = habitLogRepository
                .findByHabit_UserAndDateBetween(user, sevenDaysAgo, today);

        Map<LocalDate, Long> completedCountByDate = logsInRange.stream()
                .filter(HabitLog::isCompleted)
                .collect(Collectors.groupingBy(HabitLog::getDate, Collectors.counting()));

        List<StatisticsResponse.DailyCompletion> dailyStats = new ArrayList<>();
        long totalCompletedInRange = 0;

        for (int i = 0; i < 7; i++) {
            LocalDate date = sevenDaysAgo.plusDays(i);
            long completed = completedCountByDate.getOrDefault(date, 0L);
            totalCompletedInRange += completed;

            double percentage = totalActiveHabits == 0
                    ? 0
                    : (completed * 100.0) / totalActiveHabits;

            dailyStats.add(StatisticsResponse.DailyCompletion.builder()
                    .date(date)
                    .completedCount((int) completed)
                    .totalActiveHabits(totalActiveHabits)
                    .completionPercentage(percentage)
                    .build());
        }

        double overallRate = (totalActiveHabits == 0)
                ? 0
                : (totalCompletedInRange * 100.0) / (totalActiveHabits * 7);

        List<Challenge> allChallenges = challengeRepository.findByUser(user);
        int total = allChallenges.size();
        int active = (int) allChallenges.stream().filter(c -> c.getStatus().equals("ACTIVE")).count();
        int completed = (int) allChallenges.stream().filter(c -> c.getStatus().equals("COMPLETED")).count();
        int failed = (int) allChallenges.stream().filter(c -> c.getStatus().equals("FAILED")).count();

        return StatisticsResponse.builder()
                .last7DaysHabitCompletion(dailyStats)
                .totalChallenges(total)
                .activeChallenges(active)
                .completedChallenges(completed)
                .failedChallenges(failed)
                .totalActiveHabits(totalActiveHabits)
                .overallHabitCompletionRate(overallRate)
                .build();
    }
}