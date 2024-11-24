package com.example.newsfeed.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class PagedFeedResponseDto {
    private List<FeedResponseDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

    // 기본 생성자
    public void PagedFeedResponse() {}

    // 모든 필드를 포함한 생성자

    public PagedFeedResponseDto(List<FeedResponseDto> content, int pageNo, int pageSize,
                                long totalElements, int totalPages, boolean last) {
        this.content = content;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }


}
