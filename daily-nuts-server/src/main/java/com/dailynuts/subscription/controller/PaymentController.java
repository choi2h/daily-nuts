package com.dailynuts.subscription.controller;

import com.dailynuts.subscription.dto.PaymentRequestDto;
import com.dailynuts.subscription.dto.PaymentResponseDto;
import com.dailynuts.subscription.service.PaymentService;
//import com.dailynuts.util.JwtUtil; 회원파트 코드에 맞춰 변경 필요
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    //private final JwtUtil jwtUtil;

    @PostMapping("/prepare")
    public ResponseEntity<PaymentResponseDto.PrepareResponse> prepare(@RequestBody PaymentRequestDto.PrepareRequest request,
                                                                      HttpServletRequest httpRequest) {
//        Long memberId = jwtUtil.getId(httpRequest);
//        return ResponseEntity.ok(paymentService.createPrepare(memberId, request));
        return null; //추후 제거 필요
    }

    @PostMapping("/confirm")
    public ResponseEntity<PaymentResponseDto.ConfirmResponse> confirm(@RequestBody PaymentRequestDto.ConfirmRequest request,
                                                                      HttpServletRequest httpRequest) {
//        Long memberId = jwtUtil.getId(httpRequest);
//        return ResponseEntity.ok(paymentService.createConfirm(memberId, request));
        return null; //추후 제거 필요
    }

    @GetMapping("/status")
    public ResponseEntity<PaymentResponseDto.StatusResponse> getStatus(HttpServletRequest httpRequest) {
//        Long memberId = jwtUtil.getId(httpRequest);
//        return ResponseEntity.ok(paymentService.getStatusList(memberId));
        return null; //추후 제거 필요
    }
}