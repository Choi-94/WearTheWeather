package com.example.weartheweather.controller;


import com.example.weartheweather.dto.*;
import com.example.weartheweather.service.AlarmService;
import com.example.weartheweather.service.CommentService;
import com.example.weartheweather.service.MemberBoardService;
import com.example.weartheweather.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/memberBoard")
@RequiredArgsConstructor
public class MemberBoardController {
    private final MemberBoardService memberBoardService;
    private final CommentService commentService;
    private final AlarmService alarmService;

    public int countMyAlarm (HttpSession session) {
        String memberNickName = (String)session.getAttribute("memberNickName");
        List<AlarmDTO> alarmDTOList = alarmService.findByMyAlarm(memberNickName);
        int countAlarm = 0;
        if (alarmDTOList.size() != 0) {
            countAlarm = alarmDTOList.size();
        } return countAlarm;
    }

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
    public String findAll(Model model, @PageableDefault(size = 15)Pageable pageable,@RequestParam(value = "type", required = false, defaultValue = "") String type,
                          @RequestParam(value = "q", required = false, defaultValue = "") String q) {
        Page<MemberBoardDTO> memberBoardDTOList = memberBoardService.findAll(pageable,type,q);
        int startPage = Math.max(1,memberBoardDTOList.getPageable().getPageNumber()-4);
        int endPage = Math.min(memberBoardDTOList.getTotalPages(), memberBoardDTOList.getPageable().getPageNumber()+4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boardList", memberBoardDTOList);
        model.addAttribute("type", type);
        model.addAttribute("q", q);
        return "/codiContestPages/boardList";
    }

    @GetMapping("/rankingList")
    public String rankingList(Model model) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime lastWeek = today.minusWeeks(1);
        List<MemberBoardDTO> weeklyLikesList = memberBoardService.weeklyLikesList(today, lastWeek);
        model.addAttribute("boardList", weeklyLikesList);
        return "/codiContestPages/boardRankingList";
    }

    @GetMapping("/detail/{id}")
    public String findById(@PathVariable Long id, Model model, HttpSession session,
                           HttpServletRequest req, HttpServletResponse res) {
        memberBoardService.CookieBoardView(id, req, res);
        String memberNickName = (String)session.getAttribute("memberNickName");
        String boardLikes = "";
        if(memberNickName!=null){
            MemberBoardLikesDTO memberBoardLikesDTO = memberBoardService.findByBoardLikes(memberNickName, id);
            if (memberBoardLikesDTO != null) {
                boardLikes = "bi-heart-fill";
            }
        }else{

        }

        int countBoardLikes = memberBoardService.countBoardLikes(id);
        MemberBoardDTO memberBoardDTO = memberBoardService.findById(id);
        List<CommentDTO> commentDTOList = commentService.findAll(id);

        if (commentDTOList.size() > 0) {
            model.addAttribute("commentList", commentDTOList);
        } else {
            model.addAttribute("commentList", null);
        }
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

    @GetMapping("/countLikes/{id}")
    public ResponseEntity<Integer> countLikes(@PathVariable Long id) {
        int countBoardLikes = memberBoardService.countBoardLikes(id);
        return new ResponseEntity<>(countBoardLikes, HttpStatus.OK);
    }



    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id,HttpSession session, Model model) {
        int countMyAlarm = this.countMyAlarm(session);
        model.addAttribute("countMyAlarm", countMyAlarm);
        MemberBoardDTO memberBoardDTO = memberBoardService.findById(id);
        model.addAttribute("board", memberBoardDTO);
        return "/codiContestPages/boardUpdate";
    }

    @PutMapping("/")
    public ResponseEntity update(@RequestBody MemberBoardDTO memberBoardDTO, HttpSession session) {
        String memberNickName = (String)session.getAttribute("memberNickName");
        memberBoardService.update(memberBoardDTO, memberNickName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        memberBoardService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
