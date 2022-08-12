package khm.board.domain;

import khm.board.dto.BoardDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Board extends BaseTimeEntity{

    @Id
    @GeneratedValue
    @Column(name="board_id")
    private Long id;
    private String title;
    private String content;
    private String createdBy;
    private Long countVisit;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public  Board() {}
    @Builder
    public Board(String title, String content, String createdBy, Long countVisit) {
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.countVisit = countVisit;
    }

    //연관관계 편의 메서드
    public void createBoard(Member loginMember) {
        this.setMember(loginMember);
        loginMember.getBoards().add(this);
       //this.setCreatedBy(loginMember.getLoginId());
    }

    public void countVisitIncrease() {
        this.countVisit++;
    }

    public  void change(BoardDto boardDto) {
        this.title = boardDto.getTitle();
        this.content = boardDto.getContent();
    }
}
