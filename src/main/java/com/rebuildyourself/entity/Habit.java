package com.rebuildyourself.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "habits")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name; // e.g. "Morning Gym", "Read 20 pages"

    @Column(nullable = false)
    private String category; // GYM, MEDITATION, STUDY, READING, WORK, SLEEP, WATER_INTAKE, CUSTOM

    @Column(nullable = false)
    private LocalDate createdDate;

    @Builder.Default
    private boolean active = true;

    @OneToMany(mappedBy = "habit", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<HabitLog> logs = new ArrayList<>();
}