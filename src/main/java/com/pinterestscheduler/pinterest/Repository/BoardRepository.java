package com.pinterestscheduler.pinterest.Repository;

import com.pinterestscheduler.pinterest.Entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findBoardByBoardId(String id);
}
