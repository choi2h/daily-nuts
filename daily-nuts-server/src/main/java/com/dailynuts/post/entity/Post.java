package com.dailynuts.post.entity;

import com.dailynuts.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity @Getter
@Table(name = "post")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "writer", length = 50, nullable = false)
    private String writer;

    @Column(name = "contents", columnDefinition = "TEXT", nullable = false)
    private String contents;

    @Column(name = "is_pinned", columnDefinition = "TINYINT(1)", nullable = false)
    private boolean isPinned;

    @Column(name = "like_count", nullable = false)
    private int likeCount;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Post(Member member, Category category, String title, String contents, boolean isPinned, int likeCount) {
        this.member = member;
        this.category = category;
        this.title = title;
        this.contents = contents;
        this.isPinned = isPinned;
        this.likeCount = likeCount;
        this.writer = member.getName();
    }

    public void update(String title, String contents, Category category) {
        this.title = title;
        this.contents = contents;
        this.category = category;
    }

    public void setPinned(boolean pinned) {
        this.isPinned = pinned;
    }

}
