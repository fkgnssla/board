package khm.board.service;

import khm.board.domain.Board;
import khm.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;

    public Long saveBoard(Board board) {
        return boardRepository.save(board).getId();
    }

    @Transactional(readOnly = true)
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Board findOne(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                ()->{throw new IllegalArgumentException("찾는 게시물이 존재하지 않습니다.");}
        );
    }

    public Board countVisitIncrease(Long boardId) {
        Board board = findOne(boardId);
        board.countVisitIncrease();
        return board;
    }
}
