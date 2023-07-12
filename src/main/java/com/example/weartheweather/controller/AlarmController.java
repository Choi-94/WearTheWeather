package com.example.weartheweather.controller;

import com.example.weartheweather.dto.AlarmDTO;
import com.example.weartheweather.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {
    private final AlarmService alarmService;



    @PostMapping("/likesAlarm")
    public ResponseEntity likesAlarm(@RequestBody AlarmDTO alarmDTO, HttpSession session) {
        String memberNickName = (String)session.getAttribute("memberNickName");
        alarmService.likesAlarm(alarmDTO, memberNickName);
        System.out.println("memberNickName = " + memberNickName);
        System.out.println("alarmDTO = " + alarmDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
