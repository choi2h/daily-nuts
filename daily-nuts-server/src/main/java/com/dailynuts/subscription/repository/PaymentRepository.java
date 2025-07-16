package com.dailynuts.subscription.repository;

import com.dailynuts.subscription.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
