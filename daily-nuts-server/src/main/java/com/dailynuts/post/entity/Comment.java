package com.dailynuts.post.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Document(collection = "comment")
public class Comment {
    @Id
    private String id;
    private Long postId;
    private Long memberId;
    private String writer;
    private String contents;
    private String parentCommentId; // null이면 일반 댓글
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Comment(Long memberId, Long postId, String writer, String parentCommentId, String contents) {
        this.postId = postId;
        this.memberId = memberId;
        this.writer = writer;
        this.contents = contents;
        this.parentCommentId = parentCommentId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

}
