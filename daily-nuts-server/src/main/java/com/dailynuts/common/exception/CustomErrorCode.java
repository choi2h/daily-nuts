package com.dailynuts.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    // 회원 M
    MEMBER_NOT_EXIST(HttpStatus.NOT_FOUND, "M001", "아이디 혹은 비밀번호를 확인해주세요"),
    PASSWORD_DOSE_NOT_MATCH(HttpStatus.UNAUTHORIZED, "M002", "아이디 혹은 비밀번호를 확인해주세요"),
    TOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED, "M003", "로그인이 인증되지 않았습니다"),
    TOKEN_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "M008", "토큰 생성 과정에서 오류가 생겼습니다"),
    SECRET_KEY_INITIALIZATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "M009", "시크릿 키 초기화 도중 오류가 생겼습니다"),
    TOKEN_VALIDATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "M010", "토큰 유효성 검사를 실패하였습니다"),
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "M004", "권한이 없습니다"),
    EXPERT_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "M005", "이미 존재하는 전문가 정보가 있습니다."),
    EXPERT_NOT_EXIST(HttpStatus.NOT_FOUND, "M006", "전문가 정보가 존재하지 않습니다."),
    NOT_EXPERT_MEMBER(HttpStatus.BAD_REQUEST, "M007", "전문가 회원이 아닙니다."),

    // 파일 F
    FILE_SAVE_FAIL(HttpStatus.SERVICE_UNAVAILABLE, "F001", "파일을 저장하지 못했습니다."),


    // 게시글 P
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "요청하신 게시글을 찾을 수 없습니다."),
    POST_LIKE_NOT_EXIST(HttpStatus.NOT_FOUND, "P002", "좋아요 기록이 존재하지 않습니다."),
    POST_LIKE_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "P003", "이미 좋아요된 게시물입니다."),
    SEARCH_KEYWORD_EMPTY(HttpStatus.BAD_REQUEST, "P004", "검색어를 입력해주세요"),
    INVALID_SORT_CRITERIA(HttpStatus.BAD_REQUEST, "P005", "유효하지 않은 정렬 기준입니다."),

    // 댓글 C
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "C001", "존재하지 않는 댓글입니다."),
    COMMENT_UNAUTHORIZED(HttpStatus.FORBIDDEN, "C002", "댓글 작성자만 수정할 수 있습니다."),
    COMMENT_CONTENT_INVALID(HttpStatus.BAD_REQUEST, "C003", "댓글 내용은 1자 이상 100자 이하로 입력해주세요."),
    COMMENT_PARENT_NOT_FOUND(HttpStatus.NOT_FOUND, "C004", "존재하지 않는 부모 댓글입니다."),
    COMMENT_REPLY_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "C005", "대댓글에는 다시 답글을 달 수 없습니다."),
    COMMENT_DELETE_UNAUTHORIZED(HttpStatus.FORBIDDEN, "C006", "댓글 작성자만 삭제할 수 있습니다."),

    // 카테고리 T
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "T001", "존재하지 않는 카테고리입니다."),

    // 구독 S

    SUBSCRIPTION_NOT_EXIST(HttpStatus.BAD_REQUEST, "S001", "작가에 대한 구독자 권한이 없습니다."),

    // 결제 A
    PRODUCT_NOT_EXIST(HttpStatus.NOT_FOUND, "A001", "존재하지 않는 상품입니다."),
    INVALID_PAYMENT_REQUEST(HttpStatus.BAD_REQUEST, "A002", "유효하지 않은 결제 요청입니다."),
    INVALID_PAYMENT_INFO(HttpStatus.BAD_REQUEST, "A003", "결제 정보가 유효하지 않습니다."),
    PORTONE_CALL_FAILED(HttpStatus.BAD_REQUEST, "A004", "결제 정보 조회에 실패했습니다."),

    // 알림 N
    NOTIFICATION_NOT_EXIST(HttpStatus.NOT_FOUND, "N001", "존재하지 않는 알림입니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
