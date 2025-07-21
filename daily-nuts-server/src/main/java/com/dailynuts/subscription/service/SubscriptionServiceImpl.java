package com.dailynuts.subscription.service;

import com.dailynuts.member.repository.MemberRepository;
import com.dailynuts.post.entity.Post;
import com.dailynuts.post.repository.CommentRepository;
import com.dailynuts.post.repository.PostLikeRepository;
import com.dailynuts.post.repository.PostRepository;
import com.dailynuts.subscription.dto.SubscribedPostResponseDto;
import com.dailynuts.subscription.dto.SubscribedPostsResponseDto;
import com.dailynuts.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService{

    private final SubscriptionRepository subscriptionRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional(readOnly = true)
    public SubscribedPostsResponseDto getSubscribedFeed(Long memberId, Long categoryId, int page, int size, String criteria) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, criteria));

        List<Long> expertIds = subscriptionRepository.findExpertIdsBySubscriberId(memberId);

        if (expertIds.isEmpty()) {
            return new SubscribedPostsResponseDto(List.of(), 0, page);
        }

        Page<Post> postPage = (categoryId == null || categoryId == 0L)
                ? postRepository.findByMember_IdIn(expertIds, pageable)
                : postRepository.findByMember_IdInAndCategory_Id(expertIds, categoryId, pageable);

        List<SubscribedPostResponseDto> postDtoList = new ArrayList<>();
        for (Post post : postPage.getContent()) {
            int likeCount = postLikeRepository.countByPostId(post.getId());
            int commentCount = commentRepository.countByPostId(post.getId());

            postDtoList.add(SubscribedPostResponseDto.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .contents(post.getContents())
                    .likeCount(likeCount)
                    .commentCount(commentCount)
                    .isLiked(false)
                    .writer(post.getWriter())
                    .build());
        }

        return new SubscribedPostsResponseDto(
                postDtoList,
                postPage.getTotalPages(),
                postPage.getNumber()
        );
    }
}
