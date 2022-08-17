package khm.board.service;

import khm.board.domain.Board;
import khm.board.dto.BoardDto;
import khm.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;

    public Long saveBoard(Board board) {
        return boardRepository.save(board).getId();
    }

    @Transactional(readOnly = true)
    public Page<Board> findAll(String title, String content, Pageable pageable) {
        if(title.equals("")) return boardRepository.findAll(pageable);
        else {
            return boardRepository.findByTitleContainingOrContentContaining(title, content, pageable);
        }
    }

    @Transactional(readOnly = true)
    public Board findOne(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                ()->{throw new IllegalArgumentException("찾는 게시물이 존재하지 않습니다.");}
        );
    }

    public void countVisitIncrease(Long boardId) {
        Board board = findOne(boardId);
        board.countVisitIncrease();
        return;
    }

    public String myContent(Long id1, Long id2) {
        if(id1==id2) return "true";
        else return "false";
    }

    public void updateBoard(Board board) {
        Board findBoard = findOne(board.getId());
        findBoard.change(new BoardDto(board));
    }
}
