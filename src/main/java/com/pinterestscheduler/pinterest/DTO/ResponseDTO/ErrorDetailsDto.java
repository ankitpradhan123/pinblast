package com.pinterestscheduler.pinterest.DTO.ResponseDTO;

import java.util.Date;

public class ErrorDetailsDto {
    private Date timestamp;
    private String error;
    private String details;

    public ErrorDetailsDto(Date timestamp, String error, String details) {
        this.timestamp = timestamp;
        this.error = error;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getError() {
        return error;
    }

    public String getDetails() {
        return details;
    }
}
