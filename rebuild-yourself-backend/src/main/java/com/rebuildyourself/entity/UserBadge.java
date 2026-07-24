package com.rebuildyourself.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "user_badges", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "badge_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBadge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_id", nullable = false)
    private Badge badge;

    @Column(nullable = false)
    private LocalDate earnedDate;
}