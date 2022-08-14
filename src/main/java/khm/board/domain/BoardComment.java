package khm.board.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class BoardComment {
    @Id
    @GeneratedValue
    private Long id;

    private String comment;
    private LocalDateTime createdDate;
    private String createdBy;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;

    public BoardComment() {}

    @Builder
    public BoardComment(String comment, LocalDateTime createdDate, String createdBy, Member member, Board board) {
        this.comment = comment;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.member = member;
        this.board = board;
    }

}
