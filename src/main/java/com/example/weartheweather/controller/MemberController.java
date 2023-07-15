package com.example.weartheweather.controller;

import com.example.weartheweather.dto.MemberDTO;
import com.example.weartheweather.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @GetMapping("/memberLogin")
    public String loginForm(@RequestParam(value = "redirectURI", defaultValue = "/") String redirectURI,
                            Model model) {
        model.addAttribute("redirectURI", redirectURI);
        return "memberPages/memberLogin";
    }

    @GetMapping("/memberSave")
    public String saveForm(){
        return "/memberPages/memberSave";
    }
//    @PostMapping("/login")
//    public String memberLogin(@ModelAttribute MemberDTO memberDTO, HttpSession session,
//                              @RequestParam(value = "redirectURI") String redirectURI) {
//        System.out.println("MemberController.memberLogin");
//        System.out.println("URI" + redirectURI);
//        boolean loginResult = memberService.login(memberDTO);
//        if (loginResult) {
//            session.setAttribute("loginEmail", memberDTO.getMemberEmail());
//            return "redirect:" + redirectURI;
//        } else {
//            return "memberPages/memberLogin";
//        }
//    }



    @PostMapping("/memberSave")
    public String save(@ModelAttribute MemberDTO memberDTO, Model model){
        memberService.save(memberDTO);
        System.out.println("memberDTO = " + memberDTO);
        return "/memberPages/memberLogin";

    }

    @PostMapping("/emailcheck")
    public ResponseEntity emailcheck(@ModelAttribute MemberDTO memberDTO){

        int res = memberService.emailcheck(memberDTO.getMemberEmail());
        System.out.println("res"+res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @PostMapping("/nickcheck")
    public ResponseEntity nickcheck(@ModelAttribute MemberDTO memberDTO){

        int res = memberService.nickcheck(memberDTO.getMemberNickName());
        System.out.println("res"+res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @PostMapping("/login/axios")
    public ResponseEntity login(@RequestBody MemberDTO memberDTO, HttpSession session) throws Exception{
        MemberDTO memberDTO1 = memberService.loginAxios(memberDTO);
        System.out.println("닉네임 세션값 확인"+memberDTO1.getMemberNickName());
        if(memberDTO1==null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }else{
            session.setAttribute("memberNickName", memberDTO1.getMemberNickName());
            session.setAttribute("loginEmail", memberDTO1.getMemberEmail());
            return new ResponseEntity<>(memberDTO1,HttpStatus.OK);
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }
    @GetMapping("/mypage")
    public String mypageForm(HttpSession session, Model model){
        String value = (String) session.getAttribute("loginEmail");
        System.out.println("value = " + value);
        MemberDTO memberDTO = memberService.findByEmail(value);
        System.out.println("마이페이지 memberDTO = " + memberDTO);
        if(memberDTO!=null){
            model.addAttribute("member",memberDTO);
            return "/myInfoPages/myInfoUpdate";
        }else{
            return "/myInfoPages/myInfoUpdate";
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody MemberDTO memberDTO){
        System.out.println("memberDTO수정 = " + memberDTO);
        memberService.update(memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/addLikePoint/{writerId}")
    public ResponseEntity addLikePoint (@PathVariable Long writerId) {
        memberService.addLikePoint(writerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/addCommentPoint/{writerId}")
    public ResponseEntity addCommentPoint (@PathVariable Long writerId) {
        memberService.addCommentPoint(writerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/addSavePoint/{memberNickName}")
    public ResponseEntity addSavePoint (@PathVariable String memberNickName) {
        memberService.addSavePoint(memberNickName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
