package khm.board.dto;

import khm.board.domain.Board;
import khm.board.domain.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class MemberDto {
    private Long id;
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
    private String email;

    @Builder
    public MemberDto(String loginId, String password, String email) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
    }
    public MemberDto(Member member) {
        id = member.getId();
        loginId = member.getLoginId();
        password = member.getPassword();
        email = member.getEmail();
    }
    public Member toEntity() {
        return Member.builder()
                .loginId(loginId)
                .password(password)
                .email(email).build();
    }
}
