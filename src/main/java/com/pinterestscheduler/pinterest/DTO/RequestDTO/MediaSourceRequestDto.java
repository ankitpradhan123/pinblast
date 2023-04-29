package com.pinterestscheduler.pinterest.DTO.RequestDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class MediaSourceRequestDto implements Serializable {
    @NotNull(message = "source_type cannot be null")
    @NotEmpty(message = "source_type key is missing/source_type value is empty")
    @JsonProperty(value = "source_type")
    private String sourceType;
    private String url = null;
    @JsonProperty(value = "cover_image_url")
    private String coverImageUrl = null;
    @JsonProperty(value = "media_id")
    private String mediaId = null;
}
