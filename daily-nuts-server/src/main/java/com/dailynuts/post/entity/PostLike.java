package com.dailynuts.post.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity @Getter
@Table(name = "likes",
        uniqueConstraints = {
            @UniqueConstraint(name = "UniquePostIdAndMemberId", columnNames = {"postId", "memberId"})
        })
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PostLike {

    @Id
    @Column(name = "likes_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public PostLike(Long postId, Long memberId) {
        this.postId = postId;
        this.memberId = memberId;
    }
}
