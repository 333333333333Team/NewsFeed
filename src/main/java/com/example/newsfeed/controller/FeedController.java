package com.example.newsfeed.controller;


import com.example.newsfeed.dto.FeedResponseDto;
import com.example.newsfeed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeedController {
    private final FeedService feedService;

    //전체 조회
    @GetMapping("/feeds")
    public ResponseEntity<List<FeedResponseDto>> findAll(){
        return ResponseEntity.ok().body(feedService.allFeeds());
    }

    //단건 조회
    @GetMapping("/feeds/{id}")
    public ResponseEntity<FeedResponseDto> findone(@PathVariable Long id){
        return ResponseEntity.ok().body(feedService.findById(id));
    }


}
