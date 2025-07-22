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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationInfoServiceImpl implements NotificationInfoService {
    private final MessageFormatter messageFormatter;
    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;
    private final NotificationConnectServiceImpl notificationConnectService;
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
                .message(message)
                .build();

        if(type.equals(NotificationType.LIKES) || type.equals(NotificationType.COMMENT)) {
            notification.setPostId(postId);
        }
        notificationRepository.save(notification);
        notificationConnectService.sendMessageToClient(targetMemberId, message);
    }

    public NotificationResponseDto getNotifications(Long memberId) {
        log.debug("Get notifications for memberId: {}", memberId);
        List<Notification> notifications = notificationRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId);
        log.debug("Notifications found for memberId: {} , count={}", memberId, notifications.size());
        return notificationMapper.toNotificationResponseDto(notifications);
    }

    @Override
    public void updateNotificationRead(String notificationId, Long memberId) {
        log.debug("Update notification read. notificationId: {}, memberId: {}", notificationId, memberId);

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.NOTIFICATION_NOT_EXIST));

        if(!Objects.equals(notification.getMemberId(), memberId)) {
            throw new CustomException(CustomErrorCode.PERMISSION_DENIED);
        }

        notification.readNotification();
        notificationRepository.save(notification);
    }
}
