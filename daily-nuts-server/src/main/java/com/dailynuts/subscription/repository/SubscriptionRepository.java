package com.dailynuts.subscription.repository;

import com.dailynuts.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findAllBySubscriberIdOrderByStartedAtDesc(Long subscriberId);
    Optional<Subscription> findByExpertIdAndSubscriberId(Long expertId, Long subscriberId);
    boolean existsBySubscriberIdAndExpertId(Long subscriberId, Long expertId);
    Long countByExpertId(Long expertId);

}
