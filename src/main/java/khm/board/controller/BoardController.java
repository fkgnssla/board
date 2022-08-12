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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/boardContent/{boardId}")
    public String content(@PathVariable Long boardId, Model model,
                          @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto) {
        Board board = boardService.countVisitIncrease(boardId);
        String mine = boardService.myContent(memberDto.getId(), board.getMember().getId());
        BoardDto boardDto = new BoardDto(board);

        model.addAttribute("boardDto", boardDto);
        model.addAttribute("mine", mine);
        return "board/boardContent";
    }

    @GetMapping("/edit/{boardId}")
    public String editForm(@PathVariable Long boardId, Model model) {
        Board board = boardService.findOne(boardId);
        BoardDto boardDto = new BoardDto(board);
        model.addAttribute("boardDto", boardDto);
        return "board/editBoardForm";
    }

    @PostMapping("/edit/{boardId}")
    public String edit(@PathVariable Long boardId, @ModelAttribute BoardDto boardDto,
                       RedirectAttributes redirectAttributes) {
        Board board = boardService.findOne(boardId);
        board.change(boardDto);
        boardService.updateBoard(board);

        redirectAttributes.addAttribute("boardId",board.getId());
        return "redirect:/board/boardContent/{boardId}";
    }
}
