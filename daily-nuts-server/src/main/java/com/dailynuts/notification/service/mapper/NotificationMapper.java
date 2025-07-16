package com.dailynuts.notification.service.mapper;

import com.dailynuts.notification.dto.NotificationDto;
import com.dailynuts.notification.dto.NotificationResponseDto;
import com.dailynuts.notification.entity.Notification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationMapper {

    public NotificationResponseDto toNotificationResponseDto(List<Notification> notifications) {
        NotificationResponseDto notificationResponseDto = new NotificationResponseDto();
        notifications.forEach(notification -> {
            NotificationDto dto = toNotificationDto(notification);
            notificationResponseDto.addNotification(dto);
        });

        return notificationResponseDto;
    }

    public NotificationDto toNotificationDto(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId())
                .type(notification.getType().name())
                .message(notification.getMessage())
                .postId(notification.getPostId())
                .createdAt(notification.getCreatedAt())
                .isRead(notification.isRead())
                .build();
    }
}
