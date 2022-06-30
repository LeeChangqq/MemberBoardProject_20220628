package com.its.all.controller;

import com.its.all.dto.CommentDTO;
import com.its.all.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/comment")
@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @GetMapping("/save")
    public String save(@ModelAttribute CommentDTO commentDTO, @RequestParam("memberId") Long id, @RequestParam("boardId") Long id3) {
        System.out.println(commentDTO);
        System.out.println(id);
        Long id2 = commentService.save(commentDTO, id, id3);
        return "redirect:/board/" + id2;
    }

}
