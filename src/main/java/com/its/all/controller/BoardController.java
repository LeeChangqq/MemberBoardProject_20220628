package com.its.all.controller;

import com.its.all.common.PagingConst;
import com.its.all.dto.BoardDTO;
import com.its.all.dto.CommentDTO;
import com.its.all.service.BoardService;
import com.its.all.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RequestMapping("/board")
@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<BoardDTO> boardList = boardService.paging(pageable); // pageable 인터페이스
        model.addAttribute("boardList", boardList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < boardList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : boardList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "boardPages/paging";
    }
    @GetMapping("/save")
    public String saveForm() {
        return "boardPages/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        boardService.save(boardDTO);
        return "redirect:/board";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model, @PageableDefault(page = 1) Pageable pageable) {
        System.out.println(id);
        BoardDTO boardDTO = boardService.detail(id);
        model.addAttribute("board",boardDTO);
        Page<CommentDTO> commentList = boardService.paging2(pageable); // pageable 인터페이스
        model.addAttribute("commentList", commentList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < commentList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : commentList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "boardPages/detail";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);
        return "redirect:/board";
    }
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.detail2(id);
        model.addAttribute("board", boardDTO);
        return "boardPages/update";
    }
    @PostMapping("/update")
    public String update (@ModelAttribute BoardDTO boardDTO) throws IOException{
        boardService.update(boardDTO);
        return "redirect:/board/";
    }
    //    @GetMapping("/search")
//    public String search(@RequestParam(value = "q") String q, Model model, @PageableDefault(page = 1) Pageable pageable,@RequestParam("type") String type) {
//        model.addAttribute("q",q);
//        model.addAttribute("type",type);
//        List<BoardDTO> search = boardService.search(q,type);
//        model.addAttribute("searchList", search);
//        Page<BoardDTO> boardList = boardService.paging(pageable);
//        model.addAttribute("boardList", boardList);
//        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
//        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < boardList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : boardList.getTotalPages();
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);
//        return "boardPages/search";
//    }
    @GetMapping("/search")
    public String search(@RequestParam("type") String type,
                         @RequestParam("q") String q, Model model,
                         @PageableDefault(page = 1)Pageable pageable) {
        model.addAttribute("q",q);
        model.addAttribute("type",type);
        Page<BoardDTO> boardList = boardService.search(type, q, pageable);

        model.addAttribute("boardList", boardList);

        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT-1)< boardList.getTotalPages())?startPage + PagingConst.BLOCK_LIMIT -1 : boardList.getTotalPages();
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        return "/boardPages/search";
    }
}