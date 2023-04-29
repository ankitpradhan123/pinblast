package com.pinterestscheduler.pinterest.DTO.RequestDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
public class SchedulePinRequestDTO {

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    private ZoneId timeZone;

    private PinRequestDto pin;

}
