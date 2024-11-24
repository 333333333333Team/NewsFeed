package com.example.newsfeed.service;


import com.example.newsfeed.dto.FeedRequestDto;
import com.example.newsfeed.dto.FeedResponseDto;
import com.example.newsfeed.dto.FriendResponseDto;
import com.example.newsfeed.dto.PagedFeedResponseDto;
import com.example.newsfeed.entity.Feed;
import com.example.newsfeed.entity.Friend;
import com.example.newsfeed.repository.FeedRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;
    private final FriendService friendService;

    public List<FeedResponseDto> allFeeds(){
        return feedRepository.findAll()
                .stream()
                .map(FeedResponseDto::toDto)
                .toList();
    }

    //페이지 네이션 조회
    public PagedFeedResponseDto getAllFeedsPaginated(int page,int size){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Feed> feedPage = feedRepository.findAll(pageRequest);

        List<FeedResponseDto> content = feedPage.getContent()
                .stream()
                .map(FeedResponseDto::toDto)
                .collect(Collectors.toList());

        return new PagedFeedResponseDto(
                content,
                feedPage.getNumber(),
                feedPage.getSize(),
                feedPage.getTotalElements(),
                feedPage.getTotalPages(),
                feedPage.isLast()
        );
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
        Feed feed = new Feed(feedRequestDto.getNickName(),feedRequestDto.getUserId(),feedRequestDto.getPhone(),feedRequestDto.getEmail(),feedRequestDto.getContent());
        Feed saveFeed = feedRepository.save(feed);
        return FeedResponseDto.toDto(saveFeed);
    }


    @Transactional
    public FeedResponseDto updateFeed(Long id, FeedRequestDto feedRequestDto){
        Feed feed = findFeedById(id);
        feed.update(feedRequestDto.getContent());
        return FeedResponseDto.toDto(feed);
    }

    //userId 값으로 피드들 불러오기
    public List<FeedResponseDto> userFeeds(Long userId){
        return feedRepository.findByUserId(userId)
                .stream()
                .map(FeedResponseDto::toDto)
                .toList();
    }

    //userId 값으로 나와 친구의 피드들 불러오기
    public List<FeedResponseDto> meAndFriends(Long userId){
        List<FriendResponseDto> friend = friendService.getFriends(userId);
        List<Long> ids = new ArrayList<>(friend.stream()
                        .map(FriendResponseDto::getTargetId)
                        .toList());
        ids.add(userId);

        return feedRepository.findByUserIdIn(ids)
                .stream()
                .map(FeedResponseDto::toDto)
                .toList();
    }
}
