package com.yjh.newsfeeds.service;


import com.yjh.newsfeeds.dto.FeedResponseDto;
import com.yjh.newsfeeds.entity.Feed;
import com.yjh.newsfeeds.repository.Feedrepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {
    //private final MemberRepository memberRepository;
    private final Feedrepository feedRepository;

    public FeedResponseDto enroll(String content){

       // Member findMember = memberRepository.findMemberByUsernameOrElseThrow(username);
        Feed feed = new feed(content);
        feed.setMember(findMember);
        feedRepository.save(feed);

        return new  FeedResponseDto(feed.getId(), feed.getContent(), feed.getNickname());
    }

    public List<FeedResponseDto> allFeeds(){
        return feedRepository.findAll()
                .stream()
                .map(FeedResponseDto::toDto)
                .toList();
    }

}
