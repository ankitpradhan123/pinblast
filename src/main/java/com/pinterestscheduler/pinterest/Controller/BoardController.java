package com.pinterestscheduler.pinterest.Controller;

import com.pinterestscheduler.pinterest.DTO.RequestDTO.BoardRequestDto;
import com.pinterestscheduler.pinterest.DTO.ResponseDTO.BoardResponseDto;
import com.pinterestscheduler.pinterest.Service.ServiceImpl.BoardServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    private BoardServiceImpl boardService;

    public BoardController(BoardServiceImpl boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public ResponseEntity<BoardResponseDto> createBoard(@Valid @RequestBody BoardRequestDto boardRequestDto) {
        return new ResponseEntity<>(boardService.createBoard(boardRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> getAllBoards() {
        return new ResponseEntity<>(boardService.getAllBoards(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDto> getBoardById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(boardService.getBoardById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBoardById(@PathVariable(value = "id") Long id) {
        boardService.deleteBoard(id);
        return new ResponseEntity<>("Board with id "+id+" deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardResponseDto> updateBoardById(@PathVariable(name = "id") Long id, @Valid @RequestBody BoardRequestDto boardRequestDto) {
        return new ResponseEntity<>(boardService.updateBoard(id, boardRequestDto), HttpStatus.OK);
    }
}
