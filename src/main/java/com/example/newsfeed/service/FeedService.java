package com.example.newsfeed.service;


import com.example.newsfeed.dto.FeedRequestDto;
import com.example.newsfeed.dto.FeedResponseDto;
import com.example.newsfeed.dto.FriendResponseDto;
import com.example.newsfeed.dto.PagedFeedResponseDto;
import com.example.newsfeed.entity.Feed;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.repository.FeedRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;
    private final FriendService friendService;
    private final UserService userService;

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

    public  void deleteFeed(Long id,Long userId){
        Feed feed = findFeedById(id);
        if(!Objects.equals(feed.getId(), userId)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "본인의 게시물만 삭제할 수 있습니다.");
        }
        feedRepository.deleteById(id);
    }

    @Transactional
    public FeedResponseDto createFeed(Long userId, FeedRequestDto feedRequestDto){
        User user = userService.findUserById(userId);
        String content = feedRequestDto.getContent();
        Feed feed = new Feed(userId, content, user.getEmail(), user.getNickName(), user.getPhone());
        Feed saveFeed = feedRepository.save(feed);
        return FeedResponseDto.toDto(saveFeed);
    }


    @Transactional
    public FeedResponseDto updateFeed(Long id, Long userId, FeedRequestDto feedRequestDto){
        Feed feed = findFeedById(id);
        if(!Objects.equals(feed.getId(), userId)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "본인의 게시물만 수정할 수 있습니다.");
        }
        feed.setContent(feedRequestDto.getContent());
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
