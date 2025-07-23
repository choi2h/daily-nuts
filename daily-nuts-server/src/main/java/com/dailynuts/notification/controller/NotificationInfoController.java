package com.dailynuts.notification.controller;

import com.dailynuts.notification.dto.NotificationResponseDto;
import com.dailynuts.notification.service.NotificationInfoServiceImpl;
import com.dailynuts.security.jwt.JwtMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class NotificationInfoController {

    private final NotificationInfoServiceImpl notificationInfoService;

    @GetMapping("/notifications")
    public ResponseEntity<NotificationResponseDto> getNotifications(@AuthenticationPrincipal JwtMember memberInfo) {
        NotificationResponseDto responseDto = notificationInfoService.getNotifications(memberInfo.getId());
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/notification/{notificationId}")
    public ResponseEntity<Void> updateIsRead(
            @PathVariable String notificationId,
            @AuthenticationPrincipal JwtMember memberInfo) {
        notificationInfoService.updateNotificationRead(notificationId, memberInfo.getId());

        return ResponseEntity.ok().build();
    }
}
