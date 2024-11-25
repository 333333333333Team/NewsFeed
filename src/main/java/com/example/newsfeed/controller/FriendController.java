package com.example.newsfeed.controller;

import com.example.newsfeed.dto.FriendRequestDto;
import com.example.newsfeed.dto.FriendResponseDto;
import com.example.newsfeed.service.FriendService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/friend")
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @GetMapping
    public ResponseEntity<List<FriendResponseDto>> getFriendList(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("SESSION_KEY");
        List<FriendResponseDto> friends = friendService.getFriends(userId);
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addFriendRequest(HttpServletRequest request, @RequestBody FriendRequestDto friendRequestDTO) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("SESSION_KEY");
        try {
            friendService.addFriendRequest(userId, friendRequestDTO.getTargetId());
            return ResponseEntity.ok("친구 요청이 성공적으로 전송되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("유효하지 않은 사용자입니다.");
        }
    }

    @DeleteMapping("/{targetId}")
    public ResponseEntity<String> deleteFriend(HttpServletRequest request, @PathVariable Long targetId) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("SESSION_KEY");
        try {
            friendService.deleteFriend(userId, targetId);
            return ResponseEntity.ok("친구가 성공적으로 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("친구를 찾을 수 없습니다.");
        }
    }

    @GetMapping("/{targetId}")
    public ResponseEntity<?> findFriend(HttpServletRequest request, @PathVariable Long targetId) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("SESSION_KEY");
        try {
            FriendResponseDto friend = friendService.findFriend(userId, targetId);
            return ResponseEntity.ok(friend);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("친구를 찾을 수 없습니다.");
        }
    }

    @PatchMapping("/{userId}/{targetId}")
    public ResponseEntity<String> acceptFriendRequest(HttpServletRequest request, @PathVariable Long userId, @PathVariable Long targetId) {
        HttpSession session = request.getSession(false);
        Long sessionUserId = (Long) session.getAttribute("SESSION_KEY");
        if (!sessionUserId.equals(targetId)) {
            return ResponseEntity.badRequest().body("로그인된 사용자와 대상 ID가 일치하지 않습니다.");
        }
        try {
            friendService.acceptFriendRequest(userId, targetId);
            return ResponseEntity.ok("친구 요청이 수락되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("유효하지 않은 요청 상태입니다.");
        }
    }

    @DeleteMapping("/{userId}/{targetId}/reject")
    public ResponseEntity<String> rejectFriendRequest(HttpServletRequest request, @PathVariable Long userId, @PathVariable Long targetId) {
        HttpSession session = request.getSession(false);
        Long sessionUserId = (Long) session.getAttribute("SESSION_KEY");
        if (!sessionUserId.equals(targetId)) {
            return ResponseEntity.badRequest().body("로그인된 사용자와 대상 ID가 일치하지 않습니다.");
        }
        try {
            friendService.rejectFriendRequest(userId, targetId);
            return ResponseEntity.ok("친구 요청이 거절되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("유효하지 않은 요청 상태입니다.");
        }
    }
}
