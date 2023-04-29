package com.pinterestscheduler.pinterest.DTO.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class MediaTypeResponseDto {
    @JsonProperty(value = "media_type")
    private String mediaType;
}
