package com.dailynuts.notification.service;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.member.entity.Member;
import com.dailynuts.member.repository.MemberRepository;
import com.dailynuts.notification.dto.NotificationResponseDto;
import com.dailynuts.notification.entity.Notification;
import com.dailynuts.notification.entity.NotificationType;
import com.dailynuts.notification.repository.NotificationRepository;
import com.dailynuts.notification.service.mapper.NotificationMapper;
import com.dailynuts.notification.service.message.MessageFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationInfoService {
    private final MessageFormatter messageFormatter;
    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;
    private final NotificationConnectService notificationConnectService;
    private final MemberRepository memberRepository;

    public void createNotification(NotificationType type, Long triggerMemberId, Long targetMemberId, Long postId) {
        log.debug("Create {} notification. triggerMemberId: {}, targetMemberId: {}, postId:{}",
                type, triggerMemberId, targetMemberId, postId);
        Member member = memberRepository.findById(triggerMemberId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));

        String message = messageFormatter.getMessage(type, member.getName());
        Notification notification = Notification.builder()
                .memberId(targetMemberId)
                .type(type)
                .postId(postId)
                .message(message)
                .build();
        notificationRepository.save(notification);
        notificationConnectService.sendToClient(targetMemberId, message);
    }

    public NotificationResponseDto getNotifications(Long memberId) {
        log.debug("Get notifications for memberId: {}", memberId);
        List<Notification> notifications = notificationRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId);
        log.debug("Notifications found for memberId: {} , count={}", memberId, notifications.size());
        return notificationMapper.toNotificationResponseDto(notifications);
    }
}
