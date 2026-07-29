package com.rebuildyourself.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "challenge_days")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id", nullable = false)
    private Challenge challenge;

    @Column(nullable = false)
    private int dayNumber;

    @Column(nullable = false)
    private LocalDate date;

    @Builder.Default
    private boolean completed = false; // tick = true, cross = false
}