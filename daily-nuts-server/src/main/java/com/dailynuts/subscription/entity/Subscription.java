package com.dailynuts.subscription.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "subscription")
@NoArgsConstructor
public class Subscription {

    @Id
    @Column(name = "subscription_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expert_id", nullable = false)
    private Long expertId;

    @Column(name = "subscriber_id", nullable = false)
    private Long subscriberId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Column(name = "is_active", columnDefinition = "TINYINT(1)", nullable = false)
    private boolean isActive;

    @Column(name = "started_at", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "ended_at", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime endedAt;

    @Builder
    public Subscription(Long expertId, Long subscriberId, Payment payment, boolean isActive, LocalDateTime startedAt, LocalDateTime endedAt) {
        this.expertId = expertId;
        this.subscriberId = subscriberId;
        this.payment = payment;
        this.isActive = isActive;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }
}
