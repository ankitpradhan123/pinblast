package com.pinterestscheduler.pinterest.Controller;

import com.pinterestscheduler.pinterest.DTO.RequestDTO.SchedulePinRequestDTO;
import com.pinterestscheduler.pinterest.DTO.ResponseDTO.SchedulePinResponseDTO;
import com.pinterestscheduler.pinterest.Entities.Board;
import com.pinterestscheduler.pinterest.Quartz.Jobs.PinJob;
import com.pinterestscheduler.pinterest.Service.ServiceImpl.BoardServiceImpl;
import jakarta.validation.Valid;
import org.quartz.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;


@RestController
@RequestMapping("/api")
public class PinSchedulerController {

    private Scheduler scheduler;
    private BoardServiceImpl boardService;

    public PinSchedulerController(BoardServiceImpl boardService, Scheduler scheduler) {
        this.boardService = boardService;
        this.scheduler = scheduler;
    }

    @PostMapping("/{cachedBoardId}/schedulePins")
    public ResponseEntity<SchedulePinResponseDTO> schedulePinRequest(@PathVariable(value = "cachedBoardId") Long cachedBoardId, @Valid @RequestBody SchedulePinRequestDTO schedulePinRequest) {
        try {
            ZonedDateTime dateTime = ZonedDateTime.of(schedulePinRequest.getDateTime(), schedulePinRequest.getTimeZone());
            if(dateTime.isBefore(ZonedDateTime.now())) {
                SchedulePinResponseDTO schedulePinResponse = new SchedulePinResponseDTO(false,
                        "dateTime must be after current time");
                return new ResponseEntity<>(schedulePinResponse,HttpStatus.BAD_REQUEST);
            }
            Board board = boardService.findBoardById(cachedBoardId);
            schedulePinRequest.getPin().setBoardId(board.getBoardId());
            JobDetail jobDetail = buildJobDetail(schedulePinRequest);
            Trigger trigger = buildJobTrigger(jobDetail, dateTime);
            scheduler.scheduleJob(jobDetail, trigger);

            SchedulePinResponseDTO scheduleEmailResponse = new SchedulePinResponseDTO(true,
                    jobDetail.getKey().getName(), jobDetail.getKey().getGroup(), "Pin Scheduled Successfully!");
            return ResponseEntity.ok(scheduleEmailResponse);
        } catch (SchedulerException ex) {

            SchedulePinResponseDTO schedulePinResponse = new SchedulePinResponseDTO(false,
                    "Error scheduling pin. Please try later!");
            return new ResponseEntity<>(schedulePinResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private JobDetail buildJobDetail(SchedulePinRequestDTO schedulePinRequest) {
        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("pin", schedulePinRequest.getPin());

        return JobBuilder.newJob(PinJob.class)
                .withIdentity(UUID.randomUUID().toString(), "pin-jobs")
                .withDescription("Send Pin Job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "pin-triggers")
                .withDescription("Send Pin Trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }
}
