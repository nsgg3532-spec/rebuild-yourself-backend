package com.rebuildyourself.repository;

import com.rebuildyourself.entity.Challenge;
import com.rebuildyourself.entity.ChallengeDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChallengeDayRepository extends JpaRepository<ChallengeDay, Long> {
    Optional<ChallengeDay> findByChallengeAndDayNumber(Challenge challenge, int dayNumber);
}