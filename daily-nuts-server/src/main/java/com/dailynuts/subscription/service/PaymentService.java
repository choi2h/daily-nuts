package com.dailynuts.subscription.service;

import com.dailynuts.subscription.dto.PaymentRequestDto;
import com.dailynuts.subscription.dto.PaymentResponseDto;

public interface PaymentService {

    PaymentResponseDto.PrepareResponse createPrepare(Long subscriberId, PaymentRequestDto.PrepareRequest request);

    PaymentResponseDto.ConfirmResponse createConfirm(Long subscriberId, PaymentRequestDto.ConfirmRequest request);

    PaymentResponseDto.StatusResponse getStatusList(Long subscriberId);
}
