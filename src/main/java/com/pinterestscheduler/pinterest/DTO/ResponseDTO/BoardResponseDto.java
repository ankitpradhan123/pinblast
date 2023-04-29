package com.pinterestscheduler.pinterest.DTO.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BoardResponseDto {
    private String id;
    private String name;
    private String description;
    private String privacy;
    @JsonProperty(value = "board_id")
    private String boardId;
    @JsonProperty(value = "pin_count")
    private int pinCount;
}
