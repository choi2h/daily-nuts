package com.dailynuts.subscription.repository;

import com.dailynuts.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByExpertIdAndSubscriberId(Long expertId, Long subscriberId);
}
