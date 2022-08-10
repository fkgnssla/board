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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        //로그인 처리
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
    public String createForm (@ModelAttribute Member member) {
        return "member/createMemberForm";
    }

    @PostMapping("/create")
    public String create (@Validated @ModelAttribute Member member, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            return "member/createMemberForm";
        }

        Member newMember = memberService.createMember(member);
        if(newMember==null) {//중복된 ID일 경우
            bindingResult.reject("duplicateID","해당 아이디가 이미 존재합니다.");
            return "member/createMemberForm";
        }

        redirectAttributes.addAttribute("memberId",member.getId());
        return "redirect:/member/saved/{memberId}";
    }

    @GetMapping("/saved/{memberId}")
    public String saved (Model model, @PathVariable Long memberId) {
        Member findMember = memberService.findOne(memberId);
        model.addAttribute("member",findMember);
        return "member/savedMemberForm";
    }
}
