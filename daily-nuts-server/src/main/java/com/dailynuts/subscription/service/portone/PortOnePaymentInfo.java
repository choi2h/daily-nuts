package com.dailynuts.subscription.service.portone;

import lombok.Getter;

@Getter
public class PortOnePaymentInfo {
    private String impUid;
    private String merchantUid;
    private int amount;
    private String status;
}
