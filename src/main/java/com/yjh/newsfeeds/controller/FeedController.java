package com.yjh.newsfeeds.controller;


import com.yjh.newsfeeds.dto.FeedResponseDto;
import com.yjh.newsfeeds.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeedController {
    private final FeedService feedService;

    @GetMapping("/feeds")
    public ResponseEntity<List<FeedResponseDto>> findAll(){
        return ResponseEntity.ok().body(feedService.allFeeds());
    }


}
