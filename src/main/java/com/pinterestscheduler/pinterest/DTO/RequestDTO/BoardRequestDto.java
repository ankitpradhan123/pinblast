package com.pinterestscheduler.pinterest.DTO.RequestDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BoardRequestDto {
    @NotEmpty(message = "name cannot be empty/name key missing")
    @NotNull(message = "name cannot be null")
    private String name;

    private String description;

    @NotEmpty(message = "privacy cannot be empty/privacy key missing")
    @NotNull(message = "privacy cannot be null")
    private String privacy;
}
