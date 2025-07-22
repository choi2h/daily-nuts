package com.dailynuts.post.service;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.member.entity.Member;
import com.dailynuts.member.repository.MemberRepository;
import com.dailynuts.post.dto.PostRequestDto;
import com.dailynuts.post.dto.PostResponseDto;
import com.dailynuts.post.entity.Category;
import com.dailynuts.post.entity.Post;
import com.dailynuts.post.repository.CategoryRepository;
import com.dailynuts.post.repository.CommentRepository;
import com.dailynuts.post.repository.PostLikeRepository;
import com.dailynuts.post.repository.PostRepository;
import com.dailynuts.post.service.mapper.PostMapper;
import com.dailynuts.security.jwt.JwtMember;
import com.dailynuts.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public PostResponseDto createPost(PostRequestDto request, JwtMember userDetails) {
        Long memberId = userDetails.getId();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.CATEGORY_NOT_FOUND));

        Post post = postMapper.getPostEntity(request, member, category);
        Post saved = postRepository.save(post);
        return postMapper.getPostResponseDto(saved, false, 0);
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id, JwtMember userDetails) {
        Post post = getPostEntityById(id);
        Long viewerId = userDetails.getId();
        Long writerId = post.getMember().getId();

        boolean isAuthor = viewerId.equals(writerId);
        boolean isPinned = post.isPinned();
        boolean isSubscribed= subscriptionRepository.existsBySubscriberIdAndExpertId(viewerId, writerId);

        if (!(isAuthor || isPinned || isSubscribed)) {
            throw new CustomException(CustomErrorCode.PERMISSION_DENIED);
        }

        return toPostResponseDto(post, userDetails.getId());
    }

    @Override
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto request, JwtMember userDetails) {
        Post post = getPostEntityById(id);
        Long memberId = userDetails.getId();
        Long writerId = post.getMember().getId();

        if (!memberId.equals(writerId)) {
            throw new CustomException(CustomErrorCode.PERMISSION_DENIED);
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                        .orElseThrow(() -> new CustomException(CustomErrorCode.CATEGORY_NOT_FOUND));

        post.update(request.getTitle(), request.getContents(), category);
        return toPostResponseDto(post, memberId);
    }

    @Override
    @Transactional
    public void deletePost(Long id, JwtMember userDetails) {
        Post post = getPostEntityById(id);
        Long memberId = userDetails.getId();
        Long writerId = post.getMember().getId();

        if (!memberId.equals(writerId)) {
            throw new CustomException(CustomErrorCode.PERMISSION_DENIED);
        }

        postRepository.delete(post);
    }

    private Post getPostEntityById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomErrorCode.POST_NOT_FOUND));
    }

    private PostResponseDto toPostResponseDto(Post post, Long memberId) {
        boolean isExist = postLikeRepository.existsPostLikeByPostIdAndMemberId(post.getId(), memberId);
        int commentCount = commentRepository.countByPostId(post.getId());
        return postMapper.getPostResponseDto(post, isExist, commentCount);
    }

}
