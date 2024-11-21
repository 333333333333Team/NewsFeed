package com.example.newsfeed.controller;

import com.example.newsfeed.dto.LoginRequestDto;
import com.example.newsfeed.dto.UserRequestDto;
import com.example.newsfeed.dto.UserResponseDto;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto ){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequestDto));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId, @RequestBody UserRequestDto userRequestDto) {
        userService.resignUser(userId, userRequestDto);
        return ResponseEntity.ok().body("정상적으로 회원탈퇴되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {
        User loginedUser = userService.loginUser(loginRequestDto);
        HttpSession session = request.getSession();
        session.setAttribute("SESSION_KEY", loginedUser.getUserId());

        return ResponseEntity.ok().body("정상적으로 로그인되었습니다.");
    }

}
