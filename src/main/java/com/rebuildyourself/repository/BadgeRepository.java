package com.rebuildyourself.repository;

import com.rebuildyourself.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
    List<Badge> findByXpRequiredLessThanEqual(int xp);
}