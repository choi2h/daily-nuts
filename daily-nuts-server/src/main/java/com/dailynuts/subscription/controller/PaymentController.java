package com.dailynuts.subscription.controller;

import com.dailynuts.member.entity.Member;
import com.dailynuts.subscription.dto.PaymentRequestDto;
import com.dailynuts.subscription.dto.PaymentResponseDto;
import com.dailynuts.subscription.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<PaymentResponseDto.PrepareResponse> prepare(@RequestBody PaymentRequestDto.PrepareRequest request,
                                                                      @AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(paymentService.createPrepare(member.getId(), request));
    }

    @PostMapping("/confirm")
    public ResponseEntity<PaymentResponseDto.ConfirmResponse> confirm(@RequestBody PaymentRequestDto.ConfirmRequest request,
                                                                      @AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(paymentService.createConfirm(member.getId(), request));
    }

    @GetMapping("/status")
    public ResponseEntity<PaymentResponseDto.StatusResponse> getStatus(@AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(paymentService.getStatusList(member.getId()));
    }
}