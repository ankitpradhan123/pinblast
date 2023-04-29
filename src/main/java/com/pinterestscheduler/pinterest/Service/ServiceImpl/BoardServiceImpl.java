package com.pinterestscheduler.pinterest.Service.ServiceImpl;

import com.pinterestscheduler.pinterest.Config.APIConfig;
import com.pinterestscheduler.pinterest.DTO.RequestDTO.BoardRequestDto;
import com.pinterestscheduler.pinterest.DTO.ResponseDTO.BoardResponseDto;
import com.pinterestscheduler.pinterest.Entities.Board;
import com.pinterestscheduler.pinterest.Exception.PinterestApiException;
import com.pinterestscheduler.pinterest.Exception.ResourceNotFoundException;
import com.pinterestscheduler.pinterest.Repository.BoardRepository;
import com.pinterestscheduler.pinterest.Service.BoardService;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private BoardRepository boardRepository;
    private RestTemplate restTemplate;

    public BoardServiceImpl(BoardRepository boardRepository) {
        restTemplate = new RestTemplate();
        this.boardRepository = boardRepository;
    }
    @Override
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto) {
        BoardResponseDto boardResponseDto;
        try {
            HttpBuilderService<BoardRequestDto> httpBuilder = new HttpBuilderService<>();
            boardResponseDto = restTemplate.postForObject(APIConfig.SANDBOX_URL + "boards", httpBuilder.buildHttpEntity(boardRequestDto), BoardResponseDto.class);
        } catch (RuntimeException e) {
            throw new PinterestApiException(e.getMessage());
        }
        Board board = boardRepository.save(mapToEntity(boardResponseDto));
        return mapToDto(board);
    }

    @Override
    public BoardResponseDto updateBoard(Long id, BoardRequestDto board) {
        Board boardToUpdate = findBoardById(id);
        try {
            HttpBuilderService<BoardRequestDto> httpBuilder = new HttpBuilderService<>();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            restTemplate.exchange(APIConfig.SANDBOX_URL + "boards/"+boardToUpdate.getBoardId(), HttpMethod.PATCH, httpBuilder.buildHttpEntity(board), Void.class);
        } catch (RuntimeException e) {
            throw new PinterestApiException(e.getMessage());
        }
        boardToUpdate.setName(board.getName());
        boardToUpdate.setDescription(board.getDescription());
        boardToUpdate.setPrivacy(board.getPrivacy());
        return mapToDto(boardRepository.save(boardToUpdate));
    }

    @Override
    public void deleteBoard(Long id) {
        Board board = findBoardById(id);
        try {
            HttpBuilderService<Void> httpBuilder = new HttpBuilderService<>();
            restTemplate.exchange(APIConfig.SANDBOX_URL + "boards/"+board.getBoardId(), HttpMethod.DELETE, httpBuilder.buildHttpEntity(), Void.class);
        } catch (RuntimeException e) {
            throw new PinterestApiException(e.getMessage());
        }
        boardRepository.deleteById(id);
    }

    @Override
    public BoardResponseDto getBoardById(Long id) {
        return mapToDto(findBoardById(id));
    }

    @Override
    public Board findBoardByBoardId(String boardId) {
        try {
            return boardRepository.findBoardByBoardId(boardId);
        } catch (RuntimeException e) {
            throw new ResourceNotFoundException("board", "boardId", boardId);
        }
    }

    @Override
    public List<BoardResponseDto> getAllBoards() {
        List<Board> boards = boardRepository.findAll();
        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();
        for (Board board : boards) {
            boardResponseDtoList.add(mapToDto(board));
        }
        return boardResponseDtoList;
    }

    public Board findBoardById(Long id) {
        Board board;
        try {
            board = boardRepository.findById(id).get();
        } catch (RuntimeException e) {
            throw new ResourceNotFoundException("Board", "id", String.valueOf(id));
        }
        return board;
    }

    private BoardResponseDto mapToDto(Board board) {
        BoardResponseDto boardResponseDto = new BoardResponseDto();
        boardResponseDto.setId(board.getId().toString());
        boardResponseDto.setName(board.getName());
        if (board.getDescription() != null) {
            boardResponseDto.setDescription(board.getDescription());
        }
        boardResponseDto.setBoardId(board.getBoardId());
        boardResponseDto.setPinCount(board.getPinCount());
        boardResponseDto.setPrivacy(board.getPrivacy());
        return boardResponseDto;
    }

    private Board mapToEntity(BoardResponseDto boardResponseDto) {
        Board board = new Board();
        board.setName(boardResponseDto.getName());
        if (boardResponseDto.getDescription() != null) {
            board.setDescription(boardResponseDto.getDescription());
        }
        if (boardResponseDto.getPrivacy() != null) {
            board.setPrivacy(boardResponseDto.getPrivacy());
        }
        board.setBoardId(boardResponseDto.getId());
        return board;
    }

    public void updateBoardPinCount(Board board, boolean increaseCount) {
        if (increaseCount) {
            board.setPinCount(board.getPinCount() + 1);
        } else {
            board.setPinCount(board.getPinCount() - 1);
        }
        boardRepository.save(board);
    }
}
