package khm.board.controller;

import khm.board.SessionConst;
import khm.board.domain.Board;
import khm.board.domain.Member;
import khm.board.dto.BoardDto;
import khm.board.dto.MemberDto;
import khm.board.service.BoardService;
import khm.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("/create")
    public String createBoard() {
        return "board/createBoardForm";
    }

    @PostMapping("/create") //리팩터링 필요할듯
    public String create(@ModelAttribute BoardDto boardDto,
                         @SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) MemberDto loginMemberDto) {
        Board board = boardDto.toEntitiy();
        board.setCountVisit(1L);
        board.setCreatedBy(loginMemberDto.getLoginId());

        Member findMember = memberService.findOne(loginMemberDto.getId());
        board.createBoard(findMember);

        boardService.saveBoard(board);
        memberService.updateMember(findMember);
        return "redirect:/board/boardList";
    }

    @GetMapping("/boardList")
    public String boardList(Model model) {
        model.addAttribute("boards", boardService.findAll());
        return "/board/boardList";
    }
}
