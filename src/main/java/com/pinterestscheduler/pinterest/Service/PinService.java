package com.pinterestscheduler.pinterest.Service;

import com.pinterestscheduler.pinterest.DTO.RequestDTO.PinRequestDto;
import com.pinterestscheduler.pinterest.DTO.ResponseDTO.PinResponseDto;

import java.util.List;

public interface PinService {

    PinResponseDto createPinForBoard(Long cachedBoardId, PinRequestDto pinRequestDto);

    PinResponseDto updatePin(Long cachedBoardId, Long cachedPinId, PinRequestDto pinRequestDto);

    PinResponseDto getPinByCachedBoardIdAndCachedPinId(Long cachedBoardId, Long cachedPinId);

    void deletePin(Long cachedBoardId, Long cachedPinId);

    List<PinResponseDto> getAllPinsByCachedBoardId(Long cachedBoardId);
}
