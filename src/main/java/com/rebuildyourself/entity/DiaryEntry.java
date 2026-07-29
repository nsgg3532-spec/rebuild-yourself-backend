package com.rebuildyourself.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "diary_entries", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "entryDate"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate entryDate;

    @Column(nullable = false)
    private String mood; // HAPPY, SAD, NEUTRAL, ANXIOUS, EXCITED, STRESSED, GRATEFUL, TIRED

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder.Default
    private int wordCount = 0;
}