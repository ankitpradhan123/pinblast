package com.pinterestscheduler.pinterest.DTO.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PinResponseDto {
    private String id;
    @JsonProperty(value = "pin_id")
    private String pinId;
    private String link;
    private String title;
    private String description;
    @JsonProperty(value = "dominant_color")
    private String dominantColor;
    @JsonProperty(value = "board_id")
    private String boardId;
    @JsonProperty(value = "board_owner")
    private BoardOwnerResponseDto boardOwner;
    @JsonProperty(value = "media")
    private MediaTypeResponseDto media;
    @JsonProperty(value = "parent_pin_id")
    private String parentPinId;
}
