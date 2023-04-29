package com.pinterestscheduler.pinterest.Service.ServiceImpl;

import com.pinterestscheduler.pinterest.Config.APIConfig;
import com.pinterestscheduler.pinterest.DTO.RequestDTO.PinRequestDto;
import com.pinterestscheduler.pinterest.DTO.ResponseDTO.BoardOwnerResponseDto;
import com.pinterestscheduler.pinterest.DTO.ResponseDTO.MediaTypeResponseDto;
import com.pinterestscheduler.pinterest.DTO.ResponseDTO.PinResponseDto;
import com.pinterestscheduler.pinterest.Entities.Board;
import com.pinterestscheduler.pinterest.Entities.Pin;
import com.pinterestscheduler.pinterest.Exception.InvalidInputException;
import com.pinterestscheduler.pinterest.Exception.PinterestApiException;
import com.pinterestscheduler.pinterest.Exception.ResourceNotFoundException;
import com.pinterestscheduler.pinterest.Repository.PinRepository;
import com.pinterestscheduler.pinterest.Service.PinService;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class PinServiceImpl implements PinService {
    private PinRepository pinRepository;
    private RestTemplate restTemplate;
    private BoardServiceImpl boardService;

    public PinServiceImpl(PinRepository pinRepository, BoardServiceImpl boardService) {
        this.pinRepository = pinRepository;
        this.boardService = boardService;
        restTemplate = new RestTemplate();
    }

    private Pin findPinById(Long id) {
        try {
           return pinRepository.findById(id).get();
        } catch (Exception e) {
            throw new ResourceNotFoundException("pin", "id", String.valueOf(id));
        }
    }

    private PinResponseDto mapToDto(Pin pin) {
        PinResponseDto pinResponseDto = new PinResponseDto();
        pinResponseDto.setId(pin.getId().toString());
        pinResponseDto.setPinId(pin.getPinId());
        pinResponseDto.setBoardId(pin.getBoardId());
        if (pin.getTitle() != null) {
            pinResponseDto.setTitle(pin.getTitle());
        }
        if (pin.getDescription() != null) {
            pinResponseDto.setDescription(pin.getDescription());
        }
        if (pin.getLink() != null) {
            pinResponseDto.setLink(pin.getLink());
        }
        if (pin.getDominantColor() != null) {
            pinResponseDto.setDominantColor(pin.getDominantColor());
        }
        if (pin.getParentPinId() != null) {
            pinResponseDto.setParentPinId(pin.getParentPinId());
        }
        if (pin.getMediaType() != null) {
            MediaTypeResponseDto mediaTypeResponseDto = new MediaTypeResponseDto();
            mediaTypeResponseDto.setMediaType(pin.getMediaType());
            pinResponseDto.setMedia(mediaTypeResponseDto);
        }
        if (pin.getBoardOwnerName() != null) {
            BoardOwnerResponseDto boardOwnerResponseDto = new BoardOwnerResponseDto();
            boardOwnerResponseDto.setUsername(pin.getBoardOwnerName());
            pinResponseDto.setBoardOwner(boardOwnerResponseDto);
        }
        return pinResponseDto;
    }

    private Pin mapToEntity(PinResponseDto pinResponseDto) {
        Pin pin = new Pin();
        pin.setPinId(pinResponseDto.getId());
        pin.setBoardId(pinResponseDto.getBoardId());
        pin.setBoardOwnerName(pinResponseDto.getBoardOwner().getUsername());
        if (pinResponseDto.getTitle() != null) {
            pin.setTitle(pinResponseDto.getTitle());
        }
        if (pinResponseDto.getDescription() != null) {
            pin.setDescription(pinResponseDto.getDescription());
        }
        if (pinResponseDto.getLink() != null) {
            pin.setLink(pinResponseDto.getLink());
        }
        if (pinResponseDto.getDominantColor() != null) {
            pin.setDominantColor(pinResponseDto.getDominantColor());
        }
        if (pinResponseDto.getParentPinId() != null) {
            pin.setParentPinId(pinResponseDto.getParentPinId());
        }
        if (pinResponseDto.getMedia() != null) {
            pin.setMediaType(pinResponseDto.getMedia().getMediaType());
        }
        return pin;
    }

    @Override
    public PinResponseDto createPinForBoard(Long cachedBoardId, PinRequestDto pinRequestDto) {
        PinResponseDto pinResponseDto;
        Board board = boardService.findBoardById(cachedBoardId);
        pinRequestDto.setBoardId(board.getBoardId());
        try {
            HttpBuilderService<PinRequestDto> httpBuilderService = new HttpBuilderService<>();
            pinResponseDto = restTemplate.postForObject(APIConfig.SANDBOX_URL+"pins", httpBuilderService.buildHttpEntity(pinRequestDto), PinResponseDto.class);
        } catch (RuntimeException e) {
            throw new PinterestApiException(e.getMessage());
        }
        boardService.updateBoardPinCount(board, true);
        Pin pin  = mapToEntity(pinResponseDto);
        pin.setBoard(board);
        return mapToDto(pinRepository.save(pin));
    }

    @Override
    public PinResponseDto updatePin(Long cachedBoardId, Long cachedPinId, PinRequestDto pinRequestDto) {
        return null;
    }

    @Override
    public PinResponseDto getPinByCachedBoardIdAndCachedPinId(Long cachedBoardId, Long cachedPinId) {
        Pin pin = findPinById(cachedPinId);
        if (!pin.getBoard().getId().equals(cachedBoardId)) {
            throw new InvalidInputException("Pin does not belong to board with id : "+cachedBoardId);
        }
        return mapToDto(pin);
    }

    @Override
    public void deletePin(Long cachedBoardId, Long cachedPinId) {
        Pin pin = findPinById(cachedPinId);
        Board board = boardService.findBoardById(cachedBoardId);
        if (!pin.getBoard().getId().equals(cachedBoardId)) {
            throw new InvalidInputException("Pin does not belong to board with id : "+cachedBoardId);
        }
        try {
            HttpBuilderService<Void> httpBuilder = new HttpBuilderService<>();
            restTemplate.exchange(APIConfig.SANDBOX_URL + "pins/"+pin.getPinId(), HttpMethod.DELETE, httpBuilder.buildHttpEntity(), Void.class);
        } catch (RuntimeException e) {
            throw new PinterestApiException(e.getMessage());
        }
        boardService.updateBoardPinCount(board, false);
        pinRepository.delete(pin);
    }

    @Override
    public List<PinResponseDto> getAllPinsByCachedBoardId(Long cachedBoardId) {
        List<Pin> pinsList = pinRepository.findAll();
        List<PinResponseDto> pinResponseDtos = new ArrayList<>();
        for (Pin pin : pinsList) {
            if (pin.getBoard().getId().equals(cachedBoardId)) {
                pinResponseDtos.add(mapToDto(pin));
            }
        }
        return pinResponseDtos;
    }
}
