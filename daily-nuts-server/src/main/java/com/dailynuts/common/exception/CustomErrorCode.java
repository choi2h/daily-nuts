package com.dailynuts.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    // 회원 M
    MEMBER_NOT_EXIST(HttpStatus.NOT_FOUND, "M001", "존재하지 않는 회원입니다."),


    // 게시글 P
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "요청하신 게시글을 찾을 수 없습니다."),

    // 댓글 C

    // 구독 S

    // 결제 M
    PRODUCT_NOT_EXIST(HttpStatus.NOT_FOUND, "M001", "존재하지 않는 상품입니다.");

    // 알림 N

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
