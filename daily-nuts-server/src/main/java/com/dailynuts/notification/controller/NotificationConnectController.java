package com.dailynuts.notification.controller;

import com.dailynuts.notification.service.NotificationConnectService;
import com.dailynuts.security.jwt.JwtMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationConnectController {
    private final NotificationConnectService notificationService;

    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@AuthenticationPrincipal JwtMember member) {
        return notificationService.getSseEmitter(member.getId());
    }
}
