package com.rebuildyourself.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "habit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_id", nullable = false)
    private Habit habit;

    @Column(nullable = false)
    private LocalDate date;

    @Builder.Default
    private boolean completed = false;
}