package com.dailynuts.subscription.service.portone;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortOnePaymentInfo {

    @JsonProperty("imp_uid")
    private String impUid;

    @JsonProperty("merchant_uid")
    private String merchantUid;

    private int amount;

    private String name;

    @JsonProperty("buyer_name")
    private String buyerName;

    @JsonProperty("pay_method")
    private String payMethod;

    @JsonProperty("pg_provider")
    private String pgProvider;

    @JsonProperty("paid_at")
    private long paidAt;

    private String status;

    @JsonProperty("receipt_url")
    private String receiptUrl;
}
