package com.example.newsfeed.controller;

import com.example.newsfeed.dto.*;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.service.FeedService;
import com.example.newsfeed.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final FeedService feedService;

    // 회원가입
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto ){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequestDto));
    }

    // 회원탈퇴
    @PutMapping("/resign")
    public ResponseEntity<String> deleteUser(HttpServletRequest request, @RequestBody UserRequestDto userRequestDto) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("SESSION_KEY");
        userService.resignUser(userId, userRequestDto);
        return ResponseEntity.ok().body("정상적으로 회원탈퇴되었습니다.");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {
        User loginedUser = userService.loginUser(loginRequestDto);
        HttpSession session = request.getSession();
        session.setAttribute("SESSION_KEY", loginedUser.getUserId());
        return ResponseEntity.ok().body("정상적으로 로그인되었습니다.");
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return ResponseEntity.ok("로그아웃 성공");
    }

    // 프로필 상세 조회
    @GetMapping("/profile/{userId}")
    public ResponseEntity<Map<String, Object>> viewProfile(@PathVariable Long userId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Map<String, Object> profile = new HashMap<>();
        // 찾으려는 프로필이 로그인중인 아이디의 프로필일때
        Long loginId = (Long) session.getAttribute("SESSION_KEY");
        if(userId.equals(loginId)) {
            // 내 프로필 조회 ( 모든정보 )
            UserResponseDto userResponseDto = userService.myProfile(loginId);
            List<FeedResponseDto> feedResponseDto = feedService.meAndFriends(userId);;
            profile.put("user", userResponseDto);
            profile.put("feed", feedResponseDto);
            return new ResponseEntity<>(profile, HttpStatus.OK);
        }
        //다른 사람의 프로필 조회 (닉네임, 이메일 , 전화번호만 출력)
        UserResponseDto userResponseDto = userService.viewProfile(userId);
        List<FeedResponseDto> feedResponseDto = feedService.userFeeds(userId);
        profile.put("user", userResponseDto);
        profile.put("feeds", feedResponseDto);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    // 프로필 수정
    @PutMapping("/profile/update")
    public ResponseEntity<UserResponseDto> updateProfile(HttpServletRequest request,@RequestBody UpdateProfileRequestDto requestDto) {
        HttpSession session = request.getSession(false);
        Long loginId = (Long) session.getAttribute("SESSION_KEY");
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateProfile(loginId, requestDto));
    }


}
