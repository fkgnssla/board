package khm.board.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Board {

    @Id
    @GeneratedValue
    @Column(name="board_id")
    private Long id;

    private String title;

    private String content;

    private LocalDateTime createdDate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
