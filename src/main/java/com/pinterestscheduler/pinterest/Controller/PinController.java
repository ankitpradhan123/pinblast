package com.pinterestscheduler.pinterest.Controller;

import com.pinterestscheduler.pinterest.DTO.RequestDTO.PinRequestDto;
import com.pinterestscheduler.pinterest.DTO.ResponseDTO.PinResponseDto;
import com.pinterestscheduler.pinterest.Service.ServiceImpl.PinServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards/")
public class PinController {
    private PinServiceImpl pinService;

    public PinController(PinServiceImpl pinService) {
        this.pinService = pinService;
    }

    @PostMapping("{cachedBoardId}/pins")
    public ResponseEntity<PinResponseDto> createPin(@PathVariable(value = "cachedBoardId") Long cachedBoardId,@Valid @RequestBody PinRequestDto pinRequestDto) {
        return new ResponseEntity<>(pinService.createPinForBoard(cachedBoardId, pinRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{cachedBoardId}/pins/{cachedPinId}")
    public ResponseEntity<PinResponseDto> getPinById(@PathVariable(value = "cachedBoardId") Long cachedBoardId, @PathVariable(value = "cachedPinId") Long cachedPinId) {
        return new ResponseEntity<>(pinService.getPinByCachedBoardIdAndCachedPinId(cachedBoardId, cachedPinId), HttpStatus.OK);
    }

    @GetMapping("/{cachedBoardId}/pins")
    public ResponseEntity<List<PinResponseDto>> getAllPins(@PathVariable(value = "cachedBoardId") Long cachedBoardId) {
        return new ResponseEntity<>(pinService.getAllPinsByCachedBoardId(cachedBoardId), HttpStatus.OK);
    }

    @DeleteMapping("/{cachedBoardId}/pins/{cachedPinId}")
    public ResponseEntity<String> deletePin(@PathVariable(value = "cachedBoardId") Long cachedBoardId, @PathVariable(value = "cachedPinId")Long cachedPinId) {
        pinService.deletePin(cachedBoardId, cachedPinId);
        return new ResponseEntity<>("pin deleted with id : "+cachedPinId, HttpStatus.OK);
    }
}
