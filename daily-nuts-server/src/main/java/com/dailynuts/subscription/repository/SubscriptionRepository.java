package com.dailynuts.subscription.repository;

import com.dailynuts.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findAllBySubscriberIdOrderByStartedAtDesc(Long subscriberId);
}
