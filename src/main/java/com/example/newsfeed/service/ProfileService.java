package com.example.newsfeed.service;


import com.example.newsfeed.dto.ProfileResponseDto;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProfileService {


    private final ProfileRepository profileRepository;

    // 프로필 조회(특정 유저 조회)
    public ProfileResponseDto findById(Long userid) {
        User user = profileRepository.findById(userid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        return new ProfileResponseDto(user.getUserid(), user.getEmail(), user.getNickName(), user.getPhone());
    }


    public void updateProfile(Long userId, String nickName, String phone, String oldPassword, String newPassword) {
        // 사용자 조회
        User user = profileRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId));

        // 비밀번호 검증
        if (!user.getPassword().equals(oldPassword)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "현재 비밀번호가 일치하지 않습니다.");
        }

        // 새 비밀번호가 있다면 비밀번호 수정
        if (newPassword != null && !newPassword.isEmpty()) {
            if (oldPassword.equals(newPassword)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "새 비밀번호는 현재 비밀번호와 달라야 합니다.");
            }
            user.updatePassword(newPassword);
        }

        // 프로필 정보 수정
        user.updateProfile(nickName, phone);

        // 저장
        profileRepository.save(user);
    }


}
