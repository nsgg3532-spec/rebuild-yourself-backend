package com.rebuildyourself.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "badges")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // e.g. "Beginner", "Consistent", "Achiever", "Legend"

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int xpRequired; // is XP tak pahunchte hi badge unlock hoga
}