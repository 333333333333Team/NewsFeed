package com.example.newsfeed.controller;

import com.example.newsfeed.dto.ProfileRequestDto;
import com.example.newsfeed.dto.ProfileResponseDto;
import com.example.newsfeed.dto.UpdateProfileRequestDto;
import com.example.newsfeed.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class SignUpController {

    private final ProfileService profileService;

    // 프로필 상세 조회 기능
    @GetMapping("/{id}/profile")
    public ResponseEntity<ProfileResponseDto> findById(@PathVariable Long userid) {
        ProfileResponseDto profileResponseDto = profileService.findById(userid);
        return new ResponseEntity<>(profileResponseDto, HttpStatus.OK);
    }


    // 프로필 수정 + 체크 기능
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProfile(
            @PathVariable Long userid,
            @RequestBody UpdateProfileRequestDto requestDto
    ) {
        profileService.updateProfile(
                userid,
                requestDto.getNickName(),
                requestDto.getPhone(),
                requestDto.getOldPassword(),
                requestDto.getNewPassword()
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
