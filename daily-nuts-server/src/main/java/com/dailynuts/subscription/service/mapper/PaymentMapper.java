package com.dailynuts.subscription.service.mapper;

import com.dailynuts.member.entity.Member;
import com.dailynuts.subscription.dto.PaymentPrepareInfoDto;
import com.dailynuts.subscription.dto.PaymentResponseDto;
import com.dailynuts.subscription.entity.Payment;
import com.dailynuts.subscription.entity.Product;
import com.dailynuts.subscription.entity.Subscription;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentMapper {

    public PaymentPrepareInfoDto toPrepareInfo(Member subscriber, Member expert, Product product) {
        return PaymentPrepareInfoDto.builder()
                .subscriberId(subscriber.getId())
                .expertId(expert.getId())
                .productId(product.getId())
                .amount(product.getPrice())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public PaymentResponseDto.PrepareResponse toPrepareResponse(String merchantUid, Member subscriber, Product product) {
        return PaymentResponseDto.PrepareResponse.builder()
                .merchantUid(merchantUid)
                .amount(product.getPrice())
                .name(product.getName())
                .buyerEmail(subscriber.getEmail())
                .buyerName(subscriber.getName())
                .buyerTel(subscriber.getPhoneNumber())
                .buyerAddr("")
                .buyerPostcode("")
                .build();
    }

    public Payment toPaymentEntity(Member member, Product product) {
        return Payment.builder()
                .member(member)
                .product(product)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public Subscription toSubscriptionEntity(Long subscriberId, PaymentPrepareInfoDto info, Payment payment, LocalDateTime now, LocalDateTime end) {
        return Subscription.builder()
                .subscriberId(subscriberId)
                .expertId(info.getExpertId())
                .payment(payment)
                .startedAt(now)
                .endedAt(end)
                .isActive(true)
                .build();
    }

    public PaymentResponseDto.ConfirmResponse toConfirmResponse(String message, Subscription subscription) {
        if (subscription == null) return new PaymentResponseDto.ConfirmResponse(message, null);
        return new PaymentResponseDto.ConfirmResponse(message,
                PaymentResponseDto.SubscriptionInfo.builder()
                        .expertId(subscription.getExpertId())
                        .subscriberId(subscription.getSubscriberId())
                        .startedAt(subscription.getStartedAt())
                        .endedAt(subscription.getEndedAt())
                        .build());
    }

    public PaymentResponseDto.StatusResponse toStatusResponse(List<Subscription> subscriptions, List<Member> experts, Product product) {
        List<PaymentResponseDto.StatusItem> result = subscriptions.stream().map(sub -> {
            String expertName = experts.stream()
                    .filter(e -> e.getId().equals(sub.getExpertId()))
                    .findFirst()
                    .map(Member::getName)
                    .orElse("알 수 없음");

            return PaymentResponseDto.StatusItem.builder()
                    .paymentId(sub.getPayment().getId())
                    .expertName(expertName)
                    .amount(product.getPrice())
                    .status(sub.getEndedAt().isAfter(LocalDateTime.now()) ? "paid" : "expired")
                    .createdAt(sub.getStartedAt())
                    .expireAt(sub.getEndedAt())
                    .build();
        }).collect(Collectors.toList());

        return PaymentResponseDto.StatusResponse.builder().payments(result).build();
    }
}
