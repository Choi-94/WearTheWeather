package com.example.weartheweather.controller;

import com.example.weartheweather.dto.AlarmDTO;
import com.example.weartheweather.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {
    private final AlarmService alarmService;



    @PostMapping("/likesAlarm")
    public ResponseEntity likesAlarm(@RequestBody AlarmDTO alarmDTO, HttpSession session) {
        String memberNickName = (String)session.getAttribute("memberNickName");
        alarmService.likesAlarm(alarmDTO, memberNickName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findByMyAlarm")
    public ResponseEntity findByMyAlarm(HttpSession session) {
        String memberNickName = (String)session.getAttribute("memberNickName");
            List<AlarmDTO> alarmDTOList = alarmService.findByMyAlarm(memberNickName);
            return new ResponseEntity<>(alarmDTOList, HttpStatus.OK);
        }


}