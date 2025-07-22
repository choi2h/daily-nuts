package com.dailynuts.subscription.repository;

import com.dailynuts.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findAllBySubscriberIdOrderByStartedAtDesc(Long subscriberId);

    Optional<Subscription> findByExpertIdAndSubscriberId(Long expertId, Long subscriberId);

    boolean existsBySubscriberIdAndExpertId(Long subscriberId, Long expertId);

    Long countByExpertId(Long expertId);

    @Query("SELECT s FROM Subscription s WHERE s.subscriberId=:subscriberId AND s.isActive=:isActive")
    List<Subscription> findAllBySubscriberId(Long subscriberId, boolean isActive);

    @Query("SELECT s.expertId FROM Subscription s WHERE s.subscriberId = :subscriberId AND s.isActive = true")
    List<Long> findExpertIdsBySubscriberId(@Param("subscriberId") Long subscriberId);

    boolean existsBySubscriberIdAndExpertIdAndIsActiveTrue(Long subscriberId, Long expertId);

    Long countByExpertIdAndIsActiveTrue(Long expertId);
}