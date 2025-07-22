package com.dailynuts.subscription.controller;

import com.dailynuts.security.jwt.JwtMember;
import com.dailynuts.subscription.dto.SubscribedPostsResponseDto;
import com.dailynuts.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts/sub")
@RequiredArgsConstructor
public class SubscriptionFeedController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<SubscribedPostsResponseDto> getSubscribedFeed(
            @AuthenticationPrincipal JwtMember member,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String criteria
    ) {
        SubscribedPostsResponseDto response = subscriptionService.getSubscribedFeed(
                member.getId(), categoryId, page, size, criteria
        );
        return ResponseEntity.ok(response);
    }
}
