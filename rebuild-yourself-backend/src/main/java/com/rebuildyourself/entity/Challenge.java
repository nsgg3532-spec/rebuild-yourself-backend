package com.rebuildyourself.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "challenges")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title; // e.g. "30 Day Challenge", "75 Hard", "Custom"

    @Column(nullable = false)
    private int totalDays;

    @Column(nullable = false)
    private LocalDate startDate;

    @Builder.Default
    private String status = "ACTIVE"; // ACTIVE, COMPLETED, FAILED

    @Builder.Default
    private int xpEarned = 0;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ChallengeDay> days = new ArrayList<>();
}