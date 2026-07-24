package com.rebuildyourself.repository;

import com.rebuildyourself.entity.Challenge;
import com.rebuildyourself.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByUser(User user);
    Optional<Challenge> findByIdAndUser(Long id, User user);
}