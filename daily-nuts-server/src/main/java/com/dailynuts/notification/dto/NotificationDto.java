package com.dailynuts.notification.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter @Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationDto {
    private String id;
    private String type;
    private String message;
    private Long postId;
    private LocalDateTime createdAt;
    private boolean isRead;
}
