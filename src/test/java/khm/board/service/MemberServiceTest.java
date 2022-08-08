package khm.board.service;

import khm.board.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Test
    public void 생성() {
        Member member1 = new Member("aa", "bb", "as");
        memberService.createMember(member1);

        Member findMember = memberService.findOne(member1.getId());
        Assertions.assertThat(findMember).isEqualTo(member1);
    }
    @Test
    public void 중복() {
        Member member1 = new Member("aa", "bb", "as");
        memberService.createMember(member1);
        Member member2 = new Member("aa", "cc", "ab");

        org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class,
                ()->memberService.createMember(member2));
    }

    @Test
    public void 삭제() {
        Member member1 = new Member("aa", "bb", "as");
        memberService.createMember(member1);

        memberService.deleteMember(member1.getId());

        org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
                ()->memberService.findOne(member1.getId()));
    }

    @Test
    public void 수정() {
        Member member1 = new Member("aa", "bb", "as");
        memberService.createMember(member1);
        member1.setPassword("cc");

        memberService.updateMember(member1);

        Member findMember = memberService.findOne(member1.getId());
        Assertions.assertThat(findMember).isSameAs(member1);
    }
}