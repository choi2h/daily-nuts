package com.dailynuts.subscription.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class PaymentRequestDto {

    @Getter
    @NoArgsConstructor
    public static class PrepareRequest {
        private Long expertId;
    }

    @Getter
    public static class ConfirmRequest {
        private String impUid;
        private String merchantUid;
    }
}
