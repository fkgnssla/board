package khm.board.controller;

import khm.board.SessionConst;
import khm.board.domain.Board;
import khm.board.domain.BoardComment;
import khm.board.domain.Member;
import khm.board.dto.BoardCommentDto;
import khm.board.dto.BoardDto;
import khm.board.dto.MemberDto;
import khm.board.service.BoardCommentService;
import khm.board.service.BoardService;
import khm.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardController {
    private final BoardService boardService;
    private final MemberService memberService;
    private final BoardCommentService boardCommentService;

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
    public String boardList(Model model,
                           @PageableDefault(page=0, size=10) Pageable pageable) {
        Page<Board> boards = boardService.findAll(pageable);

        int nowPage = boards.getPageable().getPageNumber() + 1;
        int startPage = Math.max(1, nowPage - 4);
        int endPage;
        if(boards.getTotalPages()==0) endPage = Math.min(nowPage + 5, boards.getTotalPages()+1);
        else endPage = Math.min(nowPage + 5, boards.getTotalPages());

        log.info(nowPage + " " + startPage + " " + endPage);
        model.addAttribute("boards", boards);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "/board/boardList";
    }

    @GetMapping("/boardContent/{boardId}")
    public String content(@PathVariable Long boardId, Model model,
                          @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto) {
        Board board = boardService.countVisitIncrease(boardId);
        String mine = boardService.myContent(memberDto.getId(), board.getMember().getId());
        BoardDto boardDto = new BoardDto(board);

        List<BoardComment> boardComments = boardCommentService.findAll(boardId);
        model.addAttribute("boardComments", boardComments);

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

    @PostMapping("/comment/{boardId}")
    public String addcomment(@PathVariable Long boardId, @ModelAttribute BoardCommentDto boardCommentDto,
            @SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto, RedirectAttributes redirectAttributes) {

        boardCommentDto.setCreatedDate(LocalDateTime.now());
        boardCommentDto.setCreatedBy(memberDto.getLoginId());
        boardCommentDto.setBoard(boardService.findOne(boardId));
        boardCommentDto.setMember(memberService.findOne(memberDto.getId()));

        BoardComment boardComment = boardCommentDto.toEntity();
        boardCommentService.save(boardComment);

        redirectAttributes.addAttribute("boardId", boardId);

        return "redirect:/board/boardContent/{boardId}";
    }
}
