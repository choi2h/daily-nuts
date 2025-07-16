package com.dailynuts.notification.controller;

import com.dailynuts.notification.dto.NotificationResponseDto;
import com.dailynuts.notification.service.NotificationInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationInfoController {

    private final NotificationInfoService notificationInfoService;

    @GetMapping("/{memberId}")
    public ResponseEntity<NotificationResponseDto> getNotifications(@PathVariable Long memberId) {
        //TODO 추후 member정보 security 정보로 변경
        NotificationResponseDto responseDto = notificationInfoService.getNotifications(memberId);
        return ResponseEntity.ok(responseDto);
    }
}
