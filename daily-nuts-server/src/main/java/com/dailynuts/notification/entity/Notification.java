package com.dailynuts.notification.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Getter @ToString
@NoArgsConstructor
@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;
    private Long memberId;
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    private Long postId;
    private String message;
    private LocalDateTime createdAt;
    private boolean isRead;

    @Builder
    public Notification(Long memberId, NotificationType type, Long postId, String message) {
        this.memberId = memberId;
        this.type = type;
        if(postId != null) this.postId = postId;
        this.message = message;
        this.createdAt = LocalDateTime.now();
        this.isRead = false;
    }

    public void readNotification() {
        this.isRead = true;
    }
}
