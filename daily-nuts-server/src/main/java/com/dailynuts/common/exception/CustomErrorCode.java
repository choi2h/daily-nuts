package com.dailynuts.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CustomErrorCode {
    // 회원 M
    MEMBER_NOT_EXIST(HttpStatus.NOT_FOUND, "M001", "존재하지 않는 회원입니다."),


    // 게시글 P
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "요청하신 게시글을 찾을 수 없습니다."),
    POST_LIKE_NOT_EXIST(HttpStatus.NOT_FOUND, "P002", "좋아요 기록이 존재하지 않습니다."),
    POST_LIKE_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "P003", "이미 좋아요된 게시물입니다."),
    // 댓글 C

    // 구독 S
    SUBSCRIPTION_NOT_EXIST(HttpStatus.BAD_REQUEST, "S001", "작가에 대한 구독자 권한이 없습니다."),

    // 결제 A
    PRODUCT_NOT_EXIST(HttpStatus.NOT_FOUND, "A001", "존재하지 않는 상품입니다."),
    INVALID_PAYMENT_REQUEST(HttpStatus.BAD_REQUEST, "A002", "유효하지 않은 결제 요청입니다."),
    INVALID_PAYMENT_INFO(HttpStatus.BAD_REQUEST, "A003", "결제 정보가 유효하지 않습니다."),
    PORTONE_CALL_FAILED(HttpStatus.BAD_REQUEST, "A004", "결제 정보 조회에 실패했습니다.");
  
    // 알림 N

    private final HttpStatus status;
    private final String errorCode;
    private final String message;

    CustomErrorCode(HttpStatus status, String errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }
}
