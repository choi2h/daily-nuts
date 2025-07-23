package com.dailynuts.subscription.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PaymentRequestDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PrepareRequest {
        private Long expertId;
    }

    @Getter
    public static class ConfirmRequest {
        private String impUid;
        private String merchantUid;
    }
}
