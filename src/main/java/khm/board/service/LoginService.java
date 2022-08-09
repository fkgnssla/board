package khm.board.service;

import khm.board.domain.Member;
import khm.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    public Member login(String loginId, String password) {
        Member findMember = memberRepository.findByLoginId(loginId);
        if(findMember!=null) {
            if(findMember.getPassword().equals(password)) return findMember;
        }
        return null;
    }

}
