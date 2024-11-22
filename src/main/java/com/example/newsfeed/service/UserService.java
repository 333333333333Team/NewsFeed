package com.example.newsfeed.service;

import com.example.newsfeed.config.PasswordEncoder;
import com.example.newsfeed.dto.LoginRequestDto;
import com.example.newsfeed.dto.UserRequestDto;
import com.example.newsfeed.dto.UserResponseDto;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {

        findUserByEmail(userRequestDto.getEmail());                                                     //이메일 중복검사
        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());                  //비밀번호 암호회
        //System.out.println(passwordEncoder.matches(userRequestDto.getPassword(), encodedPassword));
        User user = userRequestDto.toEntity();
        user.setPassword(encodedPassword);

        User savedUser = userRepository.save(user);
        return UserResponseDto.toDto(savedUser);
    }

    //회원탈퇴
    @Transactional
    public void resignUser(Long userId, UserRequestDto userRequestDto) {

        User user = findUserById(userId);
        String password = userRequestDto.getPassword();
        if(user.isResign()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 탈퇴한 회원입니다.");
        }
        if(passwordEncoder.matches(password, user.getPassword())){
            user.setResign(true);
            userRepository.save(user);
        }else{
            throw new ResponseStatusException(HttpStatus.CONFLICT, "비밀번호가 일치하지 않습니다.");
        }
    }

    //로그인
    public User loginUser(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail());
        if (user == null || !passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 사용자 이름 혹은 잘못된 비밀번호");
        }
        return user;
    }

    //
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    // 이메일 중복검사메서드
    public void findUserByEmail(String email) {
        if(userRepository.findByEmail(email) != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "중복된 이메일입니다.");
        }
    }

}
