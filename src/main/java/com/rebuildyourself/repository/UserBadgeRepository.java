package com.rebuildyourself.repository;

import com.rebuildyourself.entity.User;
import com.rebuildyourself.entity.UserBadge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {
    List<UserBadge> findByUser(User user);
    boolean existsByUserAndBadgeId(User user, Long badgeId);
}