package com.dailynuts.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class CommentResponseDto {

    private String id;
    private Long postId;
    private Long memberId;
    private String writer;
    private String contents;
    private LocalDateTime createdAt;

    @Builder.Default
    private List<CommentResponseDto> replies = new ArrayList<>();
}
