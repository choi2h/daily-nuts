package com.dailynuts.notification.controller;

import com.dailynuts.notification.dto.SendMessageRequest;
import com.dailynuts.notification.service.NotificationConnectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationConnectController {
    private final NotificationConnectService notificationService;

    @GetMapping(value = "/subscribe/{memberId}", produces = "text/event-stream")
    public SseEmitter subscribe(@PathVariable Long memberId) {
        return notificationService.getSseEmitter(memberId);
    }

    @PostMapping(value="/send/message")
    public void sendMessage(@RequestBody SendMessageRequest request) {
        notificationService.sendToClient(request.getMemberId(), request.getMessage());
    }
}
