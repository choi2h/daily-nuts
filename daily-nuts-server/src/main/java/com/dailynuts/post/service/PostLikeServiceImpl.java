package com.dailynuts.post.service;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.notification.entity.NotificationType;
import com.dailynuts.notification.service.NotificationInfoService;
import com.dailynuts.post.dto.PostLikeResponseDto;
import com.dailynuts.post.entity.PostLike;
import com.dailynuts.post.repository.PostLikeRepository;
import com.dailynuts.post.repository.PostRepository;
import com.dailynuts.subscription.entity.Subscription;
import com.dailynuts.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final NotificationInfoService notificationInfoService;

    @Override
    @Transactional
    public PostLikeResponseDto createPostLike(Long postId, Long memberId) {
        // 게시글 작성자 확인
        Long writerMemberId = postRepository.findMemberIdByPostId(postId);
        if(writerMemberId == null) {
            throw new CustomException(CustomErrorCode.POST_NOT_FOUND);
        }

        // 구독 권한 확인
        Subscription subscription = subscriptionRepository
                .findByExpertIdAndSubscriberId(writerMemberId, memberId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.SUBSCRIPTION_NOT_EXIST));

        if(!subscription.isActive()) {
            throw new CustomException(CustomErrorCode.SUBSCRIPTION_NOT_EXIST);
        }

        if(postLikeRepository.existsPostLikeByPostIdAndMemberId(postId, memberId)) {
            throw new CustomException(CustomErrorCode.POST_LIKE_ALREADY_EXIST);
        }

        // 좋아요 생성
        PostLike postLike = PostLike.builder().postId(postId).memberId(memberId).build();
        postLikeRepository.save(postLike);

        // 좋아요 수 증가
        postRepository.increaseLikeCount(postId);
        int likeCount = postRepository.findLikeCountById(postId);

        notificationInfoService.createNotification(NotificationType.LIKES, memberId, writerMemberId, postId);

        return getResponseDto(postId, likeCount, true);
    }

    @Override
    @Transactional
    public PostLikeResponseDto deletePostLike(Long postId, Long memberId) {
        // 게시글 존재 여부 확인
        if(!postRepository.existsById(postId)) {
            throw new CustomException(CustomErrorCode.POST_NOT_FOUND);
        }

        // 좋아요 삭제
        int deleteCount = postLikeRepository.deleteByPostIdAndMemberId(postId, memberId);
        if(deleteCount == 0) throw new CustomException(CustomErrorCode.POST_LIKE_NOT_EXIST);

        // 좋아요 수 감소
        postRepository.decreaseLikeCount(postId);
        int likeCount = postRepository.findLikeCountById(postId);

        return getResponseDto(postId, likeCount, false);
    }

    private PostLikeResponseDto getResponseDto(Long postId, int likeCount, boolean isLiked) {
        return new PostLikeResponseDto(postId, likeCount, isLiked);
    }
}
