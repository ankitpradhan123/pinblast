package com.pinterestscheduler.pinterest.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "PB_PINS")
public class Pin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "pin_id")
    private String pinId;
    private String link;
    private String title;
    private String description;
    @Column(name = "dominant_color")
    private String dominantColor;
    @Column(name = "board_id")
    private String boardId;
    @Column(name = "board_owner_name")
    private String boardOwnerName;
    @Column(name = "media_type")
    private String mediaType;
    @Column(name = "parent_pin_id")
    private String parentPinId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cache_board_id", nullable = false)
    private Board board;
}
