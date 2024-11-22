package com.example.newsfeed.service;


import com.example.newsfeed.dto.FeedResponseDto;
import com.example.newsfeed.entity.Feed;
import com.example.newsfeed.repository.Feedrepository;
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
      //  feed.setMember(findMember);
        feedRepository.save(feed);

        return new  FeedResponseDto(feed.getId(), feed.getContent(), );
    }

    public List<FeedResponseDto> allFeeds(){
        return feedRepository.findAll()
                .stream()
                .map(FeedResponseDto::toDto)
                .toList();
    }


    public FeedResponseDto findById(Long id){
        return FeedResponseDto.toDto(findFeedById(id));
    }

    private Feed findFeedById(Long id){
        return feedRepository.
    }

}
