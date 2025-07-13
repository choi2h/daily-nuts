package com.dailynuts.notification.entity;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Document(collation = "notification")
public class Notification {

    @Id
    private String id;
    private Long memberId;
    private String type;
    private Long postId;
    private LocalDateTime createdAt;
    private boolean isRead;
}
