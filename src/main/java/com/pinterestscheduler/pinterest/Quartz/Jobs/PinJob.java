package com.pinterestscheduler.pinterest.Quartz.Jobs;

import com.pinterestscheduler.pinterest.DTO.RequestDTO.PinRequestDto;
import com.pinterestscheduler.pinterest.Entities.Board;
import com.pinterestscheduler.pinterest.Service.ServiceImpl.BoardServiceImpl;
import com.pinterestscheduler.pinterest.Service.ServiceImpl.PinServiceImpl;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class PinJob extends QuartzJobBean {

    private BoardServiceImpl boardService;
    private PinServiceImpl pinService;

    public PinJob(BoardServiceImpl boardService, PinServiceImpl pinService) {
        this.boardService = boardService;
        this.pinService = pinService;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        PinRequestDto pinRequest = (PinRequestDto) jobDataMap.get("pin");
        Board board  = boardService.findBoardByBoardId(pinRequest.getBoardId());
        pinService.createPinForBoard(board.getId(), pinRequest);
    }
}
