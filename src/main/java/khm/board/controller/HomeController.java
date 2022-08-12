package khm.board.controller;

import khm.board.SessionConst;
import khm.board.dto.MemberDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                       MemberDto memberDto, Model model) {
        if(memberDto!=null) model.addAttribute("login","true");
        else model.addAttribute("login","false");
        return "home";
    }

}
