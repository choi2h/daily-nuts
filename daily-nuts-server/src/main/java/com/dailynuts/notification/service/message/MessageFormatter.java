package com.dailynuts.notification.service.message;

import com.dailynuts.notification.entity.NotificationType;
import org.springframework.stereotype.Component;

@Component
public class MessageFormatter {

    public static final String CONNECT_MESSAGE = "연결했습니다.";
    private static final String LIKE_NOTIFICATION_FORMAT = "%s님이 회원님의 게시글을 좋아합니다.";
    private static final String FOLLOW_NOTIFICATION_FORMAT = "%s님이 회원님을 구독했습니다.";
    private static final String COMMENT_NOTIFICATION_FORMAT = "%s님이 회원님의 게시글에 댓글을 작성했습니다.";

    public String getMessage(NotificationType type, String memberName) {
        return switch (type) {
            case LIKES -> String.format(LIKE_NOTIFICATION_FORMAT, memberName);
            case COMMENT -> String.format(COMMENT_NOTIFICATION_FORMAT, memberName);
            case FOLLOW -> String.format(FOLLOW_NOTIFICATION_FORMAT, memberName);
        };
    }
}
