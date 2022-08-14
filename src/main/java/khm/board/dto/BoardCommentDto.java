package khm.board.dto;

import khm.board.domain.Board;
import khm.board.domain.BoardComment;
import khm.board.domain.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardCommentDto {

    private String comment;
    private LocalDateTime createdDate;
    private String createdBy;
    private Member member;
    private Board board;

    public BoardComment toEntity() {
        return BoardComment.builder()
                .comment(comment)
                .createdDate(createdDate)
                .createdBy(createdBy)
                .member(member)
                .board(board)
                .build();
    }
}
