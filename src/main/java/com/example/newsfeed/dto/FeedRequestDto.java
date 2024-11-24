package com.example.newsfeed.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class FeedRequestDto {

    private final String content;

    @JsonCreator
    public FeedRequestDto(@JsonProperty("content")String content){

        this.content = content;

    }

}
