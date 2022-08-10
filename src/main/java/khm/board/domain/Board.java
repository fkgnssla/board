package khm.board.domain;

import lombok.Getter;
import lombok.Setter;

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

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
