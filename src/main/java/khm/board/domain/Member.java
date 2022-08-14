package khm.board.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member extends BaseTimeEntity{

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long Id;

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    private String email;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch=FetchType.LAZY)
    private List<BoardComment> boardComments = new ArrayList<>();

    @Builder
    public Member(String loginId, String password, String email) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
    }

    protected Member() {}

    public void change(Member member) {
        this.loginId = member.getLoginId();
        this.password = member.getPassword();
        this.email = member.getEmail();
    }
}
