package com.pinterestscheduler.pinterest.Service;

import com.pinterestscheduler.pinterest.DTO.RequestDTO.BoardRequestDto;
import com.pinterestscheduler.pinterest.DTO.ResponseDTO.BoardResponseDto;
import com.pinterestscheduler.pinterest.Entities.Board;

import java.util.List;

public interface BoardService {

    BoardResponseDto createBoard(BoardRequestDto board);

    BoardResponseDto updateBoard(Long id,BoardRequestDto board);

    void deleteBoard(Long id);

    BoardResponseDto getBoardById(Long id);

    Board findBoardByBoardId(String boardId);

    List<BoardResponseDto> getAllBoards();
}
