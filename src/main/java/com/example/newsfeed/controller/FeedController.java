package com.example.newsfeed.controller;


import com.example.newsfeed.dto.FeedRequestDto;
import com.example.newsfeed.dto.FeedResponseDto;
import com.example.newsfeed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeedController {
    private final FeedService feedService;

    //전체 조회
    @GetMapping("/feeds")
    public ResponseEntity<List<FeedResponseDto>> findAll( @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok().body(feedService.getAllFeedsPaginated(page, size));
    }

    //단건 조회
    @GetMapping("/feeds/{id}")
    public ResponseEntity<FeedResponseDto> findOne(@PathVariable Long id) {
        return ResponseEntity.ok().body(feedService.findById(id));
    }

    //피드 삭제
    @PreAuthorize("@feedService.isOwner(authentication,#id)")
    @DeleteMapping("/feeds/{id}")
    public ResponseEntity<String> deleteFeed(@PathVariable Long id) {
        feedService.deleteFeed(id);
        return ResponseEntity.ok().body("피드가 삭제되었습니다.");
    }

    //피드 등록
    @PostMapping("/feeds")
    public ResponseEntity<FeedResponseDto> createFeed(@RequestBody FeedRequestDto feedRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(feedService.createFeed(feedRequestDto));
    }

    //피드수정
    @PreAuthorize("@feedService.isOwner(authentication,#id)")
    @PatchMapping("/feed/{id}")
    public ResponseEntity<FeedResponseDto> updataFeed(@RequestBody FeedRequestDto feedRequestDto, @PathVariable Long id) {
        return ResponseEntity.ok().body(feedService.updateFeed(id, feedRequestDto));
    }

}
