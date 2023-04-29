package com.pinterestscheduler.pinterest.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "PB_BOARDS")
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String privacy;
    @Column(name = "board_id")
    private String boardId;
    @Column(name = "pin_count")
    private int pinCount;
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pin> pins;
}
