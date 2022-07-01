package com.its.all.controller;

import com.its.all.dto.MemberDTO;
import com.its.all.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @GetMapping("/save")
    public String saveForm () {
        return "memberPages/save";
    }
    @PostMapping("/save")
    public String save (@ModelAttribute MemberDTO memberDTO) throws IOException {
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);
        return "redirect:/";
    }
    @GetMapping("/login")
    public String loginForm () {
        return "memberPages/login";
    }
    @PostMapping("/login")
    public String login (@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO member = memberService.login(memberDTO);
        if(member != null) {
            session.setAttribute("mail", member.getMemberMail());
            session.setAttribute("id",member.getId());
            session.setAttribute("member",member);
//            return "index";
            return "redirect:/board";
        }else {
            return "memberPages/login";
        }
    }
//    @GetMapping("/ajax/{id}")
//    public @ResponseBody MemberDTO findByIdAjax(@PathVariable Long id){
//        MemberDTO memberDTO = memberService.findById(id);
//        return memberDTO;
//    }
    @PostMapping("/emailCheck")
    public @ResponseBody String check(@RequestParam String memberMail) {
        String result = memberService.check(memberMail);
        return result;
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
    @GetMapping("/")
    public String findAll(Model model){
        List<MemberDTO> memberList = memberService.findAll();
        model.addAttribute("member",memberList);
        return "memberPages/list";
    }
    @GetMapping("/delete/{id}")
    public String delete (@PathVariable Long id) {
        memberService.delete(id);
        return "redirect:/member/";
    }
    @GetMapping("/myPage")
    public String myPage() {
        return "memberPages/myPage";
    }
}
