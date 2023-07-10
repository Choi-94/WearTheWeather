package com.example.weartheweather.controller;


import com.example.weartheweather.dto.MemberBoardDTO;
import com.example.weartheweather.dto.MemberBoardLikesDTO;
import com.example.weartheweather.service.MemberBoardService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/memberBoard")
@RequiredArgsConstructor
public class MemberBoardController {
    private final MemberBoardService memberBoardService;

    @GetMapping("/save")
    public String saveForm() {
        return "/codiContestPages/boardSave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberBoardDTO memberBoardDTO, HttpSession session) throws IOException {
        String memberNickName =  (String)session.getAttribute("memberNickName");
        memberBoardService.save(memberBoardDTO, memberNickName);
        return "redirect:/memberBoard/list";
    }

    @GetMapping("/list")
    public String findAll(Model model) {
        List<MemberBoardDTO> memberBoardDTOList = memberBoardService.findAll();
        model.addAttribute("boardList", memberBoardDTOList);
        return "/codiContestPages/boardList";
    }

    @GetMapping("/detail/{id}")
    public String findById(@PathVariable Long id, Model model, HttpSession session,
                           HttpServletRequest req, HttpServletResponse res) {
        memberBoardService.CookieBoardView(id, req, res);
        String memberNickName = (String)session.getAttribute("memberNickName");
        MemberBoardLikesDTO memberBoardLikesDTO = memberBoardService.findByBoardLikes(memberNickName, id);
        String boardLikes = "";
        if (memberBoardLikesDTO != null) {
            boardLikes = "bi-heart-fill";
        }
        int countBoardLikes = memberBoardService.countBoardLikes(id);
        MemberBoardDTO memberBoardDTO = memberBoardService.findById(id);
        model.addAttribute("boardLikes", boardLikes);
        model.addAttribute("board", memberBoardDTO);
        model.addAttribute("countBoardLikes", countBoardLikes);

        return "/codiContestPages/boardDetail";
    }


    @GetMapping("/findByBoardLikes/{id}")
    public ResponseEntity<String> findByBoardLikes(@PathVariable Long id, HttpSession session) {
        String memberNickName = (String)session.getAttribute("memberNickName");
        MemberBoardLikesDTO memberBoardLikesDTO = memberBoardService.findByBoardLikes(memberNickName, id);
        if (memberBoardLikesDTO == null) {
            memberBoardService.addBoardLikes(memberNickName, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            memberBoardService.deleteBoardLikes(memberNickName, id);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        MemberBoardDTO memberBoardDTO = memberBoardService.findById(id);
        model.addAttribute("board", memberBoardDTO);
        return "/codiContestPages/boardUpdate";
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody MemberBoardDTO memberBoardDTO, HttpSession session) {
        String memberNickName = (String)session.getAttribute("memberNickName");
        memberBoardService.update(memberBoardDTO, memberNickName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping("/list")
//    public String paging(@PageableDefault(page = 1) Pageable pageable,
//                         @RequestParam(value = "type", required = false, defaultValue = "") String type,
//                         @RequestParam(value = "q", required = false, defaultValue = "") String q,
//                         Model model) {
//        System.out.println("page = " + pageable.getPageNumber());
//        Page<MemberBoardDTO> boardDTOS = memberBoardService.paging(pageable, type, q);
//        if (boardDTOS.getTotalElements() == 0) {
//            model.addAttribute("boardList", null);
//        } else {
//            model.addAttribute("boardList", boardDTOS);
//        }
//
//        int blockLimit = 3;
//        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
//        int endPage = ((startPage + blockLimit - 1) < boardDTOS.getTotalPages()) ? startPage + blockLimit - 1 : boardDTOS.getTotalPages();
//
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);
//        model.addAttribute("type", type);
//        model.addAttribute("q", q);
//
//        return "/codiContestPages/boardList";
//    }
}
