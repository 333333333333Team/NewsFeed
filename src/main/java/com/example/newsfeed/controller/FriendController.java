package com.example.newsfeed.controller;

import com.example.newsfeed.dto.FriendRequestDto;
import com.example.newsfeed.dto.FriendResponseDto;
import com.example.newsfeed.service.FriendService;
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
    public ResponseEntity<List<FriendResponseDto>> getFriendList(@PathVariable Long userId) {
        List<FriendResponseDto> friends = friendService.getFriends(userId);
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addFriendRequest(@PathVariable Long userId, @RequestBody FriendRequestDto friendRequestDTO) {
        try {
            friendService.addFriendRequest(userId, friendRequestDTO.getTargetId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{targetId}")
    public ResponseEntity<Void> deleteFriend(@PathVariable Long userId, @PathVariable Long targetId) {
        try {
            friendService.deleteFriend(userId, targetId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{targetId}")
    public ResponseEntity<FriendResponseDto> findFriend(@PathVariable Long userId, @PathVariable Long targetId) {
        try {
            FriendResponseDto friend = friendService.findFriend(userId, targetId);
            return new ResponseEntity<>(friend, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{targetId}")
    public ResponseEntity<Void> acceptFriendRequest(@PathVariable Long userId, @PathVariable Long targetId) {
        try {
            friendService.acceptFriendRequest(userId, targetId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{targetId}/reject")
    public ResponseEntity<Void> rejectFriendRequest(@PathVariable Long userId, @PathVariable Long targetId) {
        try {
            friendService.rejectFriendRequest(userId, targetId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
