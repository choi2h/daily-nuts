package com.dailynuts.notification.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class NotificationResponseDto {
    private final List<NotificationDto> notifications;

    public NotificationResponseDto() {
        this.notifications = new ArrayList<>();
    }

    public void addNotification(NotificationDto notification) {
        this.notifications.add(notification);
    }

}
