package com.example.weartheweather.controller;

import com.example.weartheweather.dto.MemberDTO;
import com.example.weartheweather.model.KakaoProfile;
import com.example.weartheweather.model.OAuthToken;
import com.example.weartheweather.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpSession;
import java.util.UUID;


@Controller
public class KakaoController {
    @Autowired
    private MemberService memberService;
    @Value("{cos.key}")
    private String cosKey;
    @GetMapping("/auth/kakao/callback")

    public String handleKakaoCallback(@RequestParam("code") String code, Model model, HttpSession session) {

        System.out.println("code = " + code);
        //POST방식으로 key= value 데이터를 요청(카카오쪽으로)

        RestTemplate rt = new RestTemplate();

        //HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody 오브젝트 생성
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id","cbfb51c5fe3c24569106a577d987998b");
        params.add("redirect_uri","http://localhost:8045/auth/kakao/callback");
        params.add("code",code);

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest =
                new HttpEntity<>(params,headers);
        //Http 요청하기 Post방식으로 - 그리고 response 변수의 응답 받음
        ResponseEntity<String> response = rt.exchange(
          "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        //Gson, Json Simple, ObjectMapper 라이브러리
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        }catch (JsonMappingException e){
            e.printStackTrace();
        }
         catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("카카오 엑세스 토큰:"+ oAuthToken.getAccess_token());
        // ---////////////////////////////////

        RestTemplate rt2 = new RestTemplate();

        //HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization","Bearer "+oAuthToken.getAccess_token());
        headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");


        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers2);
        //Http 요청하기 Post방식으로 - 그리고 response 변수의 응답 받음
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );
        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        System.out.println(response2.getBody());
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);

        }catch (JsonMappingException e){
            e.printStackTrace();
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("카카오아이디(번호)"+kakaoProfile.getId());
        System.out.println("카카오이메일"+kakaoProfile.kakao_account.getEmail());
        System.out.println("성별"+kakaoProfile.kakao_account.getGender());
        UUID garbage = UUID.randomUUID();
        System.out.println("garbage = " + garbage);

        String memberEmail = kakaoProfile.kakao_account.getEmail();
        String memberPassword = String.valueOf(garbage);
        String memberGender = kakaoProfile.kakao_account.getGender();


        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail(memberEmail);
        memberDTO.setMemberGender(memberGender);
        memberDTO.setMemberPassword(memberPassword);
        memberDTO.setMemberNickName(cosKey);

        MemberDTO kakaoMember  = memberService.findByEmail(memberEmail);
        if(kakaoMember.getMemberEmail()==null){
            memberService.save(memberDTO);
            session.setAttribute("memberNickName","저장후확인");
            return "/memberPages/KakaoSave";
        }else{
            session.setAttribute("memberNickName","확인");
            return "/memberPages/KakaoSave";
        }


    }


    @GetMapping("/memberPages/KakaoSave")
    public String KakaoSaveForm(){
        return "/memberPages/KakaoSave";
    }
}