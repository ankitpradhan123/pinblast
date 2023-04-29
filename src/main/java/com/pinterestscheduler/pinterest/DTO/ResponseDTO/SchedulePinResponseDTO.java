package com.pinterestscheduler.pinterest.DTO.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SchedulePinResponseDTO {

    private boolean success;
    private String jobId;
    private String jobGroup;
    private String message;

    public SchedulePinResponseDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public SchedulePinResponseDTO(boolean success, String jobId, String jobGroup, String message) {
        this.success = success;
        this.jobId = jobId;
        this.jobGroup = jobGroup;
        this.message = message;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public String getJobId() {
        return this.jobId;
    }

    public String getJobGroup() {
        return this.jobGroup;
    }

    public String getMessage() {
        return this.message;
    }
}
