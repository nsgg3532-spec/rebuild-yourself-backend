package com.rebuildyourself.repository;

import com.rebuildyourself.entity.DiaryEntry;
import com.rebuildyourself.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiaryEntryRepository extends JpaRepository<DiaryEntry, Long> {

    List<DiaryEntry> findByUserOrderByEntryDateDesc(User user);

    Optional<DiaryEntry> findByUserAndEntryDate(User user, LocalDate entryDate);

    List<DiaryEntry> findByUserAndEntryDateBetweenOrderByEntryDateDesc(
            User user, LocalDate startDate, LocalDate endDate);

    @Query("SELECT d FROM DiaryEntry d WHERE d.user = :user AND LOWER(d.content) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY d.entryDate DESC")
    List<DiaryEntry> findByUserAndContentContainingIgnoreCaseOrderByEntryDateDesc(
            @Param("user") User user, @Param("keyword") String keyword);
}