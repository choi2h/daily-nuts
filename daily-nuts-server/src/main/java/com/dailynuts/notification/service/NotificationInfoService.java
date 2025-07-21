package com.dailynuts.notification.service;

import com.dailynuts.notification.dto.NotificationResponseDto;
import com.dailynuts.notification.entity.NotificationType;

public interface NotificationInfoService {
    void createNotification(NotificationType type, Long triggerMemberId, Long targetMemberId, Long postId);

    NotificationResponseDto getNotifications(Long memberId);

    void updateNotificationRead(String notificationId, Long memberId);
}
