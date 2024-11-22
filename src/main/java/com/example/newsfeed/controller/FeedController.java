package com.example.newsfeed.controller;


import com.example.newsfeed.dto.FeedRequestDto;
import com.example.newsfeed.dto.FeedResponseDto;
import com.example.newsfeed.dto.PagedFeedResponseDto;
import com.example.newsfeed.service.FeedService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class FeedController {
    private final FeedService feedService;

    //전체 조회
    @GetMapping("/feeds")
    public ResponseEntity<PagedFeedResponseDto> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        PagedFeedResponseDto pagedFeedResponseDto = feedService.getAllFeedsPaginated(page, size);
        return ResponseEntity.ok().body(pagedFeedResponseDto);
    }


    //단건 조회
    @GetMapping("/feeds/{id}")
    public ResponseEntity<FeedResponseDto> findOne(@PathVariable Long id) {
        return ResponseEntity.ok().body(feedService.findById(id));
    }

    //Email 조회
    @GetMapping("/feeds/email/{email}")
    public ResponseEntity<List<FeedResponseDto>> getFeedsByEmail(@PathVariable String email) {
        List<FeedResponseDto> feeds = feedService.getFeedsByEmail(email);
        return ResponseEntity.ok(feeds);
    }


    //피드 삭제

    @DeleteMapping("/feeds/{id}")
    public ResponseEntity<String> deleteFeed(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        if (!feedService.isOwner(email, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("삭제 권한이 없습니다.");
        }
        feedService.deleteFeed(id);
        return ResponseEntity.ok("피드가 삭제되었습니다.");
    }


    //피드 등록
    @PostMapping("/feeds")
    public ResponseEntity<FeedResponseDto> createFeed(@RequestBody FeedRequestDto feedRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(feedService.createFeed(feedRequestDto));
    }

//피드수정

    @PatchMapping("/feeds/{id}")
    public ResponseEntity<FeedResponseDto> updataFeed(@RequestBody FeedRequestDto feedRequestDto, @PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        if (!feedService.isOwner(email, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok().body(feedService.updateFeed(id, feedRequestDto));
    }

}
