package khm.board.controller;

import khm.board.domain.Member;
import khm.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String login (Model model) {
        return "member/loginMemberForm";
    }

    @GetMapping("/create")
    public String createForm () {
        return "member/createMemberForm";
    }

    @PostMapping("/create")
    public String create (@ModelAttribute Member member) {
        memberService.createMember(member);
        return "member/savedMemberForm";
    }
}
