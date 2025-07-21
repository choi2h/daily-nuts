package com.dailynuts.notification.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface  NotificationConnectService {
    SseEmitter getSseEmitter(Long memberId);

    void sendMessageToClient(Long memberId, String message);
}
