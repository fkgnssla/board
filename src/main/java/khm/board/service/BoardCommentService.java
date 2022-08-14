package khm.board.service;

import khm.board.domain.BoardComment;
import khm.board.repository.BoardCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardCommentService {
    private final BoardCommentRepository boardCommentRepository;

    public void save(BoardComment boardComment) {
        boardCommentRepository.save(boardComment);
    }

    @Transactional(readOnly = true)
    public List<BoardComment> findAll() {
        return boardCommentRepository.findAll();
    }
}
