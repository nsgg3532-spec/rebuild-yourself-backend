package com.rebuildyourself.repository;

import com.rebuildyourself.entity.Roadmap;
import com.rebuildyourself.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoadmapRepository extends JpaRepository<Roadmap, Long> {
    List<Roadmap> findByUser(User user);
    Optional<Roadmap> findByIdAndUser(Long id, User user);
}