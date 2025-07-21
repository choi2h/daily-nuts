package com.dailynuts.subscription.service;

import com.dailynuts.subscription.dto.SubscribedPostsResponseDto;

public interface SubscriptionService {
    SubscribedPostsResponseDto getSubscribedFeed(Long memberId, Long categoryId, int page, int size, String criteria);
}
