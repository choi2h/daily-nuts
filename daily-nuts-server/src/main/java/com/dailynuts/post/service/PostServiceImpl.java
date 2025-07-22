package com.dailynuts.post.service;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.member.entity.Member;
import com.dailynuts.member.repository.MemberRepository;
import com.dailynuts.post.dto.PostRequestDto;
import com.dailynuts.post.dto.PostResponseDto;
import com.dailynuts.post.dto.PostsResponseDto;
import com.dailynuts.post.entity.Category;
import com.dailynuts.post.entity.Post;
import com.dailynuts.post.repository.CategoryRepository;
import com.dailynuts.post.repository.PostLikeRepository;
import com.dailynuts.post.repository.PostRepository;
import com.dailynuts.post.service.mapper.PostMapper;
import com.dailynuts.security.jwt.JwtMember;
import com.dailynuts.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PostLikeRepository postLikeRepository;

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
        return postMapper.getPostResponseDto(saved, false);
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id, JwtMember userDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomErrorCode.POST_NOT_FOUND));

        Long viewerId = userDetails.getId();;
        Long writerId = post.getMember().getId();

        boolean isAuthor = viewerId.equals(writerId);
        boolean isPinned = post.isPinned();

        boolean isSubscribed= subscriptionRepository.existsBySubscriberIdAndExpertId(viewerId, writerId);

        if (!(isAuthor || isPinned || isSubscribed)) {
            throw new CustomException(CustomErrorCode.PERMISSION_DENIED);
        }

        boolean isLiked = postLikeRepository.existsPostLikeByPostIdAndMemberId(id, viewerId);

        return postMapper.getPostResponseDto(post, isLiked);
    }

    @Override
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto request, JwtMember userDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomErrorCode.POST_NOT_FOUND));

        Long memberId = userDetails.getId();
        Long writerId = post.getMember().getId();

        if (!memberId.equals(writerId)) {
            throw new CustomException(CustomErrorCode.PERMISSION_DENIED);
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                        .orElseThrow(() -> new CustomException(CustomErrorCode.CATEGORY_NOT_FOUND));

        post.update(request.getTitle(), request.getContents(), category);
        boolean isLiked = postLikeRepository.existsPostLikeByPostIdAndMemberId(id, memberId);
        return postMapper.getPostResponseDto(post, isLiked);
    }

    @Override
    @Transactional
    public void deletePost(Long id, JwtMember userDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomErrorCode.POST_NOT_FOUND));

        Long memberId = userDetails.getId();
        Long writerId = post.getMember().getId();

        if (!memberId.equals(writerId)) {
            throw new CustomException(CustomErrorCode.PERMISSION_DENIED);
        }

        postRepository.delete(post);
    }

    @Override
    @Transactional(readOnly = true)
    public PostsResponseDto getPosts(Long categoryId, int pageNo, int size, String criteria, Long memberId) {
        Set<String> allowedCriteria = Set.of("createdAt", "likeCount", "commentCount");
        if (!allowedCriteria.contains(criteria)) {
            throw new CustomException(CustomErrorCode.INVALID_SORT_CRITERIA);
        }

        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, criteria));

        Page<Post> postPage = (categoryId == null || categoryId == 0L)
                ? postRepository.findByIsPinnedTrue(pageable)
                : postRepository.findByCategory_IdAndIsPinnedTrue(categoryId, pageable);

        List<PostResponseDto> postResponseDtoList = new ArrayList<>();

        for (Post post : postPage.getContent()) {
            boolean isExist = postLikeRepository.existsPostLikeByPostIdAndMemberId(post.getId(), memberId);
            PostResponseDto postResponseDto = postMapper.getPostResponseDto(post, isExist);
            postResponseDtoList.add(postResponseDto);
        }

        return new PostsResponseDto(
                postResponseDtoList,
                postPage.getTotalPages(),
                postPage.getNumber()
        );
    }

}
