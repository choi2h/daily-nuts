package com.dailynuts.post.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Document(collection = "comments")
public class Comment {
    @Getter
    @Id
    private ObjectId id;
    private Long postId;
    private Long memberId;
    private String writer;
    @Setter
    private String contents;
    private ObjectId parentCommentId; // null이면 일반 댓글
    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;

    @Builder
    public Comment(Long memberId, Long postId, String writer, ObjectId parentCommentId, String contents) {
        this.postId = postId;
        this.memberId = memberId;
        this.writer = writer;
        this.contents = contents;
        this.parentCommentId = parentCommentId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
