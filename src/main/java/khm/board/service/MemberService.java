package khm.board.service;

import khm.board.domain.Member;
import khm.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;

    public Member createMember(Member member) {
        if(!validateDuplicateMember(member)) {
            memberRepository.save(member);
            return member;
        }
        return null;
    }

    public void deleteMember(Long memberId) {
        memberRepository.delete(findOne(memberId));
    }

    public void updateMember(Member member) {
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(
                ()->{throw new NoSuchElementException("찾는 멤버가 존재하지 않습니다.");}
        );
        findMember.change(member);
    }

    @Transactional(readOnly = true)
    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                ()->{throw new NoSuchElementException("찾는 멤버가 존재하지 않습니다.");}
        );
    }

    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public boolean validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByLoginId(member.getLoginId());
        if(findMember!=null) {
            return true;
        }
        return false;
    }
}
