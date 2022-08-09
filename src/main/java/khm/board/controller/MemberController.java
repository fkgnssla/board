package khm.board.controller;

import khm.board.SessionConst;
import khm.board.domain.Member;
import khm.board.service.LoginService;
import khm.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm (@ModelAttribute Member member) {
        return "member/loginMemberForm";
    }

    @PostMapping("/login")
    public String login (@Validated @ModelAttribute Member member, BindingResult bindingResult,
                         HttpServletRequest request) {
        if(bindingResult.hasErrors()) { //예외 발생
            return "member/loginMemberForm";
        }

        Member loginMember = loginService.login(member.getLoginId(), member.getPassword());
        if(loginMember==null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "member/loginMemberForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:/"; //일단 홈으로 이동하게 설정
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
