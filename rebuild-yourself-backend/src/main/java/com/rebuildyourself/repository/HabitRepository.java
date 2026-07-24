package com.rebuildyourself.repository;

import com.rebuildyourself.entity.Habit;
import com.rebuildyourself.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> findByUserAndActiveTrue(User user);
    Optional<Habit> findByIdAndUser(Long id, User user);
}