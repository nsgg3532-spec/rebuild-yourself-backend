package com.rebuildyourself.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "reminders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title; // e.g. "Gym Reminder"

    @Column(nullable = false)
    private String message; // e.g. "Time for your workout!"

    @Column(nullable = false)
    private LocalTime reminderTime; // e.g. 07:00

    @Builder.Default
    private boolean active = true;
}