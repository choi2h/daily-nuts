package com.dailynuts.subscription.service;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.member.entity.Member;
import com.dailynuts.member.repository.MemberRepository;
import com.dailynuts.subscription.dto.*;
import com.dailynuts.subscription.entity.Payment;
import com.dailynuts.subscription.entity.Product;
import com.dailynuts.subscription.entity.Subscription;
import com.dailynuts.subscription.repository.PaymentRepository;
import com.dailynuts.subscription.repository.ProductRepository;
import com.dailynuts.subscription.repository.SubscriptionRepository;
import com.dailynuts.subscription.service.mapper.PaymentMapper;
import com.dailynuts.subscription.service.portone.PortOneClient;
import com.dailynuts.subscription.service.portone.PortOnePaymentInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final PaymentRepository paymentRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PortOneClient portOneClient;
    private final PaymentMapper paymentMapper;

    private final Map<String, PaymentPrepareInfoDto> prepareMap = new ConcurrentHashMap<>();

    @Override
    public PaymentResponseDto.PrepareResponse createPrepare(Long subscriberId, PaymentRequestDto.PrepareRequest request) {
        Member subscriber = memberRepository.findById(subscriberId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));
        Member expert = memberRepository.findById(request.getExpertId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));
        Product product = productRepository.findById(1L)
                .orElseThrow(() -> new CustomException(CustomErrorCode.PRODUCT_NOT_EXIST));

        String merchantUid = "payment-" + UUID.randomUUID();

        PaymentPrepareInfoDto info = paymentMapper.toPrepareInfo(subscriber, expert, product);
        prepareMap.put(merchantUid, info);

        return paymentMapper.toPrepareResponse(merchantUid, subscriber, product);
    }

    @Override
    public PaymentResponseDto.ConfirmResponse createConfirm(Long subscriberId, PaymentRequestDto.ConfirmRequest request) {
        PaymentPrepareInfoDto prepareInfo = prepareMap.get(request.getMerchantUid());
        if (prepareInfo == null) {
            throw new CustomException(CustomErrorCode.INVALID_PAYMENT_REQUEST);
        }

        PortOnePaymentInfo paymentInfo = portOneClient.getPaymentInfo(request.getImpUid());
        if (!"paid".equals(paymentInfo.getStatus()) ||
                paymentInfo.getAmount() != prepareInfo.getAmount()) {
            throw new CustomException(CustomErrorCode.INVALID_PAYMENT_INFO);
        }

        if (prepareMap.get(request.getMerchantUid()) == null) {
            throw new CustomException(CustomErrorCode.INVALID_PAYMENT_REQUEST);
        }

        Member member = memberRepository.findById(prepareInfo.getSubscriberId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));
        Product product = productRepository.findById(prepareInfo.getProductId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.PRODUCT_NOT_EXIST));

        Payment payment = paymentMapper.toPaymentEntity(member, product);
        paymentRepository.save(payment);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = now.plus(1, ChronoUnit.MONTHS).minus(1, ChronoUnit.MILLIS);

        Subscription subscription = paymentMapper.toSubscriptionEntity(subscriberId, prepareInfo, payment, now, end);
        subscriptionRepository.save(subscription);

        prepareMap.remove(request.getMerchantUid());
        return paymentMapper.toConfirmResponse("결제가 완료되었습니다.", subscription);
    }

    @Override
    public PaymentResponseDto.StatusResponse getStatusList(Long subscriberId) {
        Product product = productRepository.findById(1L)
                .orElseThrow(() -> new CustomException(CustomErrorCode.PRODUCT_NOT_EXIST));

        List<Subscription> subscriptions = subscriptionRepository.findAllBySubscriberIdOrderByStartedAtDesc(subscriberId);
        List<Long> expertIds = subscriptions.stream().map(Subscription::getExpertId).distinct().toList();
        List<Member> experts = memberRepository.findAllById(expertIds);

        return paymentMapper.toStatusResponse(subscriptions, experts, product);
    }
}
