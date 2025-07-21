package com.dailynuts.subscription.controller;

import com.dailynuts.security.jwt.JwtMember;
import com.dailynuts.subscription.dto.PaymentRequestDto;
import com.dailynuts.subscription.dto.PaymentResponseDto;
import com.dailynuts.subscription.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/prepare")
    public ResponseEntity<PaymentResponseDto.PrepareResponse> prepare(
            @RequestBody PaymentRequestDto.PrepareRequest request, @AuthenticationPrincipal JwtMember jwtMember) {
        return ResponseEntity.ok(paymentService.createPrepare(jwtMember.getId(), request));
    }

    @PostMapping("/confirm")
    public ResponseEntity<PaymentResponseDto.ConfirmResponse> confirm(@RequestBody PaymentRequestDto.ConfirmRequest request,
                                                                      @AuthenticationPrincipal JwtMember jwtMember) {
        return ResponseEntity.ok(paymentService.createConfirm(jwtMember.getId(), request));
    }

    @GetMapping("/status")
    public ResponseEntity<PaymentResponseDto.StatusResponse> getStatus(@AuthenticationPrincipal JwtMember jwtMember) {
        return ResponseEntity.ok(paymentService.getStatusList(jwtMember.getId()));
    }
}