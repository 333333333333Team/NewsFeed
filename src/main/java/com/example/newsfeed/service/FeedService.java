package com.example.newsfeed.service;


import com.example.newsfeed.dto.FeedRequestDto;
import com.example.newsfeed.dto.FeedResponseDto;
import com.example.newsfeed.dto.PagedFeedResponseDto;
import com.example.newsfeed.entity.Feed;
import com.example.newsfeed.repository.FeedRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {
    //private final MemberRepository memberRepository;
    private final FeedRepository feedRepository;


    public List<FeedResponseDto> allFeeds() {
        return feedRepository.findAll()
                .stream()
                .map(FeedResponseDto::toDto)
                .toList();
    }

    //페이지 네이션 조회
    public PagedFeedResponseDto getAllFeedsPaginated(int page, int size) {
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

    public FeedResponseDto findById(Long id) {
        return FeedResponseDto.toDto(findFeedById(id));
    }

    private Feed findFeedById(Long id) {
        return feedRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("잘못된 ID 값입니다."));
    }

    public List<FeedResponseDto> getFeedsByEmail(String email) {
        return feedRepository.findByEmail(email)
                .stream()
                .map(FeedResponseDto::toDto)
                .collect(Collectors.toList());
    }

    public void deleteFeed(Long id) {
        findFeedById(id);
        feedRepository.deleteById(id);
    }

    @Transactional
    public FeedResponseDto createFeed(FeedRequestDto feedRequestDto) {
        Feed feed = new Feed(feedRequestDto.getEmail(), feedRequestDto.getContent(),feedRequestDto.getNickname());
        Feed saveFeed = feedRepository.save(feed);
        return FeedResponseDto.toDto(saveFeed);
    }


    @Transactional
    public FeedResponseDto updateFeed(Long id, FeedRequestDto feedRequestDto) {
        Feed feed = findFeedById(id);
        feed.update(feedRequestDto.getContent());
        return FeedResponseDto.toDto(feed);
    }

    public boolean isOwner(String email, Long id) {
        FeedResponseDto feedResponseDto = findById(id);
        return feedResponseDto.getEmail().equals(email);
    }


}
