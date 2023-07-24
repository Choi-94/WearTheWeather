package com.example.weartheweather.controller;

import com.example.weartheweather.dto.AdminBoardDTO;
import com.example.weartheweather.dto.AdminBoardLikesDTO;
import com.example.weartheweather.dto.PopularKeywordsDTO;
import com.example.weartheweather.service.AdminBoardService;
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
@RequiredArgsConstructor
@RequestMapping("/adminBoard")
public class AdminBoardController {
    private final AdminBoardService adminBoardService;
    private final MemberBoardService memberBoardService;

    @GetMapping("/save")
    public String saveForm() {
        return "/adminPages/boardSave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute AdminBoardDTO adminBoardDTO) throws IOException {
        adminBoardService.save(adminBoardDTO);
        return "redirect:/adminBoard/list";
    }

    @GetMapping("/list")
    public String findAll(Model model){
        List<AdminBoardDTO> adminBoardDTOList = adminBoardService.findAll();
        System.out.println("컨트롤러adminBoardDTOList = " + adminBoardDTOList);
        model.addAttribute("adminBoardList", adminBoardDTOList);
        return "/weatherCodiPages/boardList";
    }

    @GetMapping("/detail/{id}")
    public String findById(@PathVariable Long id, HttpSession session, Model model,
                           HttpServletRequest req, HttpServletResponse res) {
        adminBoardService.CookieAdminBoardView(id, req, res);
        String memberNickName = (String)session.getAttribute("memberNickName");
        AdminBoardLikesDTO adminBoardLikesDTO = adminBoardService.findByBoardLikes(memberNickName, id);
        String boardLikes = "";
        if (adminBoardLikesDTO != null) {
            boardLikes = "bi-heart-fill";
        } else {
            boardLikes = null;
        }
        int countBoardLikes = adminBoardService.countBoardLikes(id);
        AdminBoardDTO adminBoardDTO = adminBoardService.findById(id);
        model.addAttribute("boardLikes", boardLikes);
        model.addAttribute("board", adminBoardDTO);
        model.addAttribute("countBoardLikes", countBoardLikes);
        return "/weatherCodiPages/boardDetail";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        AdminBoardDTO adminBoardDTO = adminBoardService.findById(id);
        model.addAttribute("board", adminBoardDTO);
        return "/weatherCodiPages/boardUpdate";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute AdminBoardDTO adminBoardDTO) {
        adminBoardService.update(adminBoardDTO);
        return "redirect:/adminBoard/list";
    }


    @GetMapping("/findByBoardLikes/{id}")
    public ResponseEntity<String> findByBoardLikes(@PathVariable Long id, HttpSession session) {
        String memberNickName = (String)session.getAttribute("memberNickName");
        AdminBoardLikesDTO adminBoardLikesDTO = adminBoardService.findByBoardLikes(memberNickName, id);
        System.out.println("adminBoardLikesDTO = " + adminBoardLikesDTO);
        if (adminBoardLikesDTO == null) {
            adminBoardService.addBoardLikes(memberNickName, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            adminBoardService.deleteBoardLikes(memberNickName, id);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/countLikes/{id}")
    public ResponseEntity<Integer> countLikes(@PathVariable Long id) {
        int countBoardLikes = adminBoardService.countBoardLikes(id);
        return new ResponseEntity<>(countBoardLikes, HttpStatus.OK);
    }

    @GetMapping("/firstSearch")
    public String firstSearchForm(Model model){
        List<PopularKeywordsDTO> popularKeywordsDTOList = adminBoardService.popularSearch();
        List<PopularKeywordsDTO> recentKeywordsDTOList = adminBoardService.findAllRecent();
        model.addAttribute("popularKeywordsDTOList",popularKeywordsDTOList);
        model.addAttribute("recentpopularKeywordsDTOList",recentKeywordsDTOList);
        System.out.println("popularKeywordsDTOList = " + popularKeywordsDTOList);

        return "/adminPages/firstSearch";
    }

    @PostMapping("/firstSearch")
    public ResponseEntity firstSearch(@ModelAttribute PopularKeywordsDTO popularKeywordsDTO){
        adminBoardService.popularSave(popularKeywordsDTO);
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @GetMapping("/secondSearch")
    public String secondSearchForm(Model model,@RequestParam("q")String q ,@RequestParam(value = "tall", defaultValue = "0")int tall, @RequestParam(value = "gender" ,defaultValue = "")String gender){
        //q값해당하는 adminboardList 가져오기
        System.out.println("보고싶어 젠더"+gender);
        List<AdminBoardDTO> adminBoardDTOList = adminBoardService.searchBoardList(q,tall,gender);
        System.out.println("서치리스트1 = " + adminBoardDTOList +"서치1 tall"+tall);
        model.addAttribute("qSearchList",adminBoardDTOList);


        model.addAttribute("q",q);
        return "/adminPages/secondSearch";
    }
    @PostMapping("/secondSearch")
    public ResponseEntity SecondSearch(@ModelAttribute PopularKeywordsDTO popularKeywordsDTO,Model model){
        //q값해당하는 adminboardList 가져오기
        System.out.println("dto값 확인"+popularKeywordsDTO);
        String gender = popularKeywordsDTO.getGenderm()+popularKeywordsDTO.getGenderw();
        List<AdminBoardDTO> adminBoardDTOList = adminBoardService.secondSearchBoardList(popularKeywordsDTO.getKeyword(),popularKeywordsDTO.getTall(),gender);
        System.out.println("서치리스트2 = " + adminBoardDTOList);
        adminBoardService.popularSave(popularKeywordsDTO);
        model.addAttribute("qSearchList",adminBoardDTOList);


        model.addAttribute("q",popularKeywordsDTO.getKeyword());
        return new ResponseEntity<>(HttpStatus.OK);

    }




//    @GetMapping("/likes")
//    public

}
