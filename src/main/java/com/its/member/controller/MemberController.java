package com.its.member.controller;

import com.its.member.dto.MemberDTO;
import com.its.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/save-form")
    public String saveForm(){
        return "/memberPages/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        memberService.save(memberDTO);
        return "/memberPages/login";
    }

    @GetMapping("/login-form")
    public String loginForm(){
        return "memberPages/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        MemberDTO loginDTO = memberService.login(memberDTO);
        if(loginDTO != null){
            session.setAttribute("loginEmail",loginDTO.getMemberEmail());
            session.setAttribute("id",loginDTO.getId());
            return "memberPages/main";
        }else {
            return "memberPages/login";
        }
    }

    @GetMapping("/list")
    public String findAll(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList",memberDTOList);
        return "memberPages/list";
    }

    @GetMapping("/{id}") //요청주소 예시: /member/3 (PathVariable: URL 경로에 변수를 넣어줌)
    public String findById(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "memberPages/detail";

    }

    @PostMapping("/ajax/{id}")
    public @ResponseBody MemberDTO findByAjax(@PathVariable Long id) {
        MemberDTO memberDTO = memberService.findById(id);
        return memberDTO;
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
        return "redirect:/member/";
    }
    //delete요청 삭제

    /**
     * ex) /member/3 처럼 조회(get), 저장(post), 수정(put), 삭제(delete)가 모두 똑같은 주소값을 가질 수 있음
     **/
    @DeleteMapping("/{id}")
    public ResponseEntity deleteAjax(@PathVariable Long id) {
        memberService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK); //ajax 호출한 부분에 리턴으로 200 응답을 줌. (데이터+바디)
    }





}
