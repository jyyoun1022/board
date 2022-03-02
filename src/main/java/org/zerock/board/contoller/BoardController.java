package org.zerock.board.contoller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.services.BoardService;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        log.info("list......."+pageRequestDTO);

        model.addAttribute("result",boardService.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register(){

    }
    @PostMapping("/register")
    public String registerPost(BoardDTO dto, RedirectAttributes redirectAttributes){

        Long bno = boardService.register(dto);

        redirectAttributes.addFlashAttribute("msg",bno);

        return "redirect:/board/list";
    }
    @GetMapping({"/read","/modify"})
    public void read(@ModelAttribute PageRequestDTO pageRequestDTO, Long bno, Model model){

        BoardDTO boardDTO = boardService.get(bno);

        model.addAttribute("dto",boardDTO);
    }

    @PostMapping("/remove")
    public String remove(long bno,RedirectAttributes redirectAttributes){

        boardService.removeWithReplies(bno);

        redirectAttributes.addFlashAttribute("msg",bno);

        return "redirect:/board/list";
    }

    @PostMapping("/modify")
    public String modify(BoardDTO dto,
                         @ModelAttribute PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes){

        boardService.modify(dto);

        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("type",requestDTO.getType());
        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());

        redirectAttributes.addAttribute("bno",dto.getBno());

        return "redirect:/board/read";

    }

}
