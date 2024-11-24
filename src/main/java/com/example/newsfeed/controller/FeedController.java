package com.example.newsfeed.controller;


import com.example.newsfeed.dto.FeedRequestDto;
import com.example.newsfeed.dto.FeedResponseDto;
import com.example.newsfeed.dto.PagedFeedResponseDto;
import com.example.newsfeed.service.FeedService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/feeds")
public class FeedController {
    private final FeedService feedService;

    //전체 조회
    @GetMapping
    public ResponseEntity<PagedFeedResponseDto> findAll( @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        PagedFeedResponseDto pagedFeedResponseDto = feedService.getAllFeedsPaginated(page, size);
        return ResponseEntity.ok().body(pagedFeedResponseDto);
    }

    //단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<FeedResponseDto> findOne(@PathVariable Long id) {
        return ResponseEntity.ok().body(feedService.findById(id));
    }

    //피드 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFeed(@PathVariable Long id,HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("SESSION_KEY");
        feedService.deleteFeed(id, userId);
        return ResponseEntity.ok().body("피드가 삭제되었습니다.");
    }

    //피드 등록
    @PostMapping
    public ResponseEntity<FeedResponseDto> createFeed( HttpServletRequest request, @RequestBody FeedRequestDto feedRequestDto) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("SESSION_KEY");
        return ResponseEntity.status(HttpStatus.CREATED).body(feedService.createFeed(userId, feedRequestDto));
    }

    //피드수정

    @PatchMapping("/{id}")
    public ResponseEntity<FeedResponseDto> updateFeed(@PathVariable Long id,@RequestBody FeedRequestDto feedRequestDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("SESSION_KEY");
        return ResponseEntity.ok().body(feedService.updateFeed(id, userId, feedRequestDto));
    }

}
