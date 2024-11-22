package com.example.newsfeed.service;


import com.example.newsfeed.dto.FeedRequestDto;
import com.example.newsfeed.dto.FeedResponseDto;
import com.example.newsfeed.entity.Feed;
import com.example.newsfeed.repository.FeedRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;


import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {
    //private final MemberRepository memberRepository;
    private final FeedRepository feedRepository;



    public List<FeedResponseDto> allFeeds(){
        return feedRepository.findAll()
                .stream()
                .map(FeedResponseDto::toDto)
                .toList();
    }

    //페이지 네이션 조회
    public List<FeedResponseDto> getAllFeedsPaginated(int page,int size){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createDate").descending());
        Page<Feed> feedPage = feedRepository.findAll(pageRequest);
        return feedPage.getContent()
                .stream()
                .map(FeedResponseDto::toDto)
                .toList();
    }


    public FeedResponseDto findById(Long id){
        return FeedResponseDto.toDto(findFeedById(id));
    }

    private Feed findFeedById(Long id){
        return feedRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("잘못된 ID 값입니다."));
    }

    public  void deleteFeed(Long id){
        findFeedById(id);
        feedRepository.deleteById(id);
    }

    @Transactional
    public FeedResponseDto createFeed(FeedRequestDto feedRequestDto){
        Feed feed = new Feed(feedRequestDto.getNickname(),feedRequestDto.getContent(),feedRequestDto.getPhone(),feedRequestDto.getEmail());
        Feed saveFeed = feedRepository.save(feed);
        return FeedResponseDto.toDto(saveFeed);
    }


    @Transactional
    public FeedResponseDto updateFeed(Long id, FeedRequestDto feedRequestDto){
        Feed feed = findFeedById(id);
        feed.update(feedRequestDto.getContent());
        return FeedResponseDto.toDto(feed);
    }

    public boolean isOwner(Authentication authentication ,Long id){
        FeedResponseDto feedResponseDto = findById(id);
        return feedResponseDto.getEmail().equals(authentication.getName());
    }

}
