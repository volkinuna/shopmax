package com.shopmax.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    
    //문의하기
    @GetMapping(value = "/members/qa") //접속주소 : localhost/members/qa
    public String qa() {
        return "member/qa"; //qa.html
    }

    //로그인 화면
    @GetMapping(value = "/members/login") //접속주소 : localhost/members/login
    public String loginMember() {
        return "member/memberLoginForm"; //memberLoginForm.html
    }

    //회원가입 화면
    @GetMapping(value = "/members/new") //접속주소 : localhost/members/new
    public String memberForm() {
        return "member/memberForm"; //memberForm.html
    }
}
