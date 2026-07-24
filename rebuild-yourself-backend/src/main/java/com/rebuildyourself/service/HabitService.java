package com.rebuildyourself.service;

import com.rebuildyourself.dto.*;
import com.rebuildyourself.entity.*;
import com.rebuildyourself.repository.HabitLogRepository;
import com.rebuildyourself.repository.HabitRepository;
import com.rebuildyourself.security.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitService {

    private final HabitRepository habitRepository;
    private final HabitLogRepository habitLogRepository;
    private final CurrentUserProvider currentUserProvider;

    public HabitResponse createHabit(CreateHabitRequest request) {
        User user = currentUserProvider.getCurrentUser();

        Habit habit = Habit.builder()
                .user(user)
                .name(request.getName())
                .category(request.getCategory().toUpperCase())
                .createdDate(LocalDate.now())
                .active(true)
                .build();

        habitRepository.save(habit);

        return toResponse(habit);
    }

    public List<HabitResponse> getMyHabits() {
        User user = currentUserProvider.getCurrentUser();
        return habitRepository.findByUserAndActiveTrue(user)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public HabitResponse markHabit(Long habitId, MarkHabitRequest request) {
        User user = currentUserProvider.getCurrentUser();

        Habit habit = habitRepository.findByIdAndUser(habitId, user)
                .orElseThrow(() -> new RuntimeException("Habit not found"));

        LocalDate targetDate = request.getDate() != null ? request.getDate() : LocalDate.now();

        HabitLog log = habitLogRepository.findByHabitAndDate(habit, targetDate)
                .orElse(HabitLog.builder()
                        .habit(habit)
                        .date(targetDate)
                        .build());

        log.setCompleted(request.isCompleted());
        habitLogRepository.save(log);

        return toResponse(habit);
    }

    public void deactivateHabit(Long habitId) {
        User user = currentUserProvider.getCurrentUser();
        Habit habit = habitRepository.findByIdAndUser(habitId, user)
                .orElseThrow(() -> new RuntimeException("Habit not found"));
        habit.setActive(false);
        habitRepository.save(habit);
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
                break; // aaj/us din incomplete hai, streak toot gayi
            }
        }

        return streak;
    }

    private boolean isCompletedToday(Habit habit) {
        return habitLogRepository.findByHabitAndDate(habit, LocalDate.now())
                .map(HabitLog::isCompleted)
                .orElse(false);
    }

    private HabitResponse toResponse(Habit habit) {
        return HabitResponse.builder()
                .id(habit.getId())
                .name(habit.getName())
                .category(habit.getCategory())
                .createdDate(habit.getCreatedDate())
                .active(habit.isActive())
                .currentStreak(calculateStreak(habit))
                .completedToday(isCompletedToday(habit))
                .build();
    }
}