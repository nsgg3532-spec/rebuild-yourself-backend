package com.rebuildyourself.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roadmap_steps")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoadmapStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id", nullable = false)
    private Roadmap roadmap;

    @Column(nullable = false)
    private String stepName; // e.g. "Core Java"

    @Column(nullable = false)
    private int stepOrder; // sequence: 1, 2, 3...

    @Builder.Default
    private boolean completed = false;
}