package com.dailynuts.subscription.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PaymentPrepareInfoDto {
    private Long subscriberId;
    private Long expertId;
    private Long productId;
    private int amount;
    private LocalDateTime createdAt;
}
