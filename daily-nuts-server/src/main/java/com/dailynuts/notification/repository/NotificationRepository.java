package com.dailynuts.notification.repository;

import com.dailynuts.notification.entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findAllByMemberIdOrderByCreatedAtDesc(Long memberId);
}
