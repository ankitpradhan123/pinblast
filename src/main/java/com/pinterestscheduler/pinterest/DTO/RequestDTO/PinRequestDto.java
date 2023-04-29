package com.pinterestscheduler.pinterest.DTO.RequestDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PinRequestDto implements Serializable {
    private String title;
    private String description;
    @JsonProperty(value = "board_id")
    private String boardId;
    @JsonProperty(value = "media_source")
    private MediaSourceRequestDto mediaSource;

}
