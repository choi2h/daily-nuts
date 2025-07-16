package com.dailynuts.subscription.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class PaymentResponseDto {

    @Getter
    @Builder
    public static class PrepareResponse {
        private String merchantUid;
        private int amount;
        private String name;
        private String buyerEmail;
        private String buyerName;
        private String buyerTel;
        private String buyerAddr;
        private String buyerPostcode;
    }

    @Getter
    @AllArgsConstructor
    public static class ConfirmResponse {
        private String message;
        private SubscriptionInfo subscription;
    }

    @Getter
    @Builder
    public static class SubscriptionInfo {
        private Long expertId;
        private Long subscriberId;
        private LocalDateTime startedAt;
        private LocalDateTime endedAt;
    }

    @Getter
    @Builder
    public static class StatusItem {
        private Long paymentId;
        private String expertName;
        private int amount;
        private String status; // paid or expired
        private LocalDateTime createdAt;
        private LocalDateTime expireAt;
    }

    @Getter
    @Builder
    public static class StatusResponse {
        private List<StatusItem> payments;
    }
}

