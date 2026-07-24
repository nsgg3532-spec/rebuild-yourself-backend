package com.rebuildyourself.repository;

import com.rebuildyourself.entity.Habit;
import com.rebuildyourself.entity.HabitLog;
import com.rebuildyourself.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HabitLogRepository extends JpaRepository<HabitLog, Long> {
    Optional<HabitLog> findByHabitAndDate(Habit habit, LocalDate date);
    List<HabitLog> findByHabitOrderByDateDesc(Habit habit);
    List<HabitLog> findByHabit_UserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
}