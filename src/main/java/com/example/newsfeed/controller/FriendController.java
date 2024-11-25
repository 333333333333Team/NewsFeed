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
    public ResponseEntity<Void> addFriendRequest(HttpServletRequest request, @RequestBody FriendRequestDto friendRequestDTO) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("SESSION_KEY");
        try {
            friendService.addFriendRequest(userId, friendRequestDTO.getTargetId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{targetId}")
    public ResponseEntity<Void> deleteFriend(HttpServletRequest request, @PathVariable Long targetId) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("SESSION_KEY");
        try {
            friendService.deleteFriend(userId, targetId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{targetId}")
    public ResponseEntity<FriendResponseDto> findFriend(HttpServletRequest request, @PathVariable Long targetId) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("SESSION_KEY");
        try {
            FriendResponseDto friend = friendService.findFriend(userId, targetId);
            return new ResponseEntity<>(friend, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{targetId}")
    public ResponseEntity<Void> acceptFriendRequest(HttpServletRequest request, @PathVariable Long targetId) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("SESSION_KEY");
        try {
            friendService.acceptFriendRequest(userId, targetId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{targetId}/reject")
    public ResponseEntity<Void> rejectFriendRequest(HttpServletRequest request, @PathVariable Long targetId) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("SESSION_KEY");
        try {
            friendService.rejectFriendRequest(userId, targetId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
