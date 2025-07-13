package com.dailynuts.post.entity;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Document(collation = "comment")
public class Comment {
    @Id
    private Long commentId;
    private Long memberId;
    private Long postId;
    private String writer;
    private Long parentCommentId;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
