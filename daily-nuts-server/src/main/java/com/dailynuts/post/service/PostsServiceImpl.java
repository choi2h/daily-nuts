package com.dailynuts.post.service;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.post.dto.PostResponseDto;
import com.dailynuts.post.dto.PostsResponseDto;
import com.dailynuts.post.entity.Post;
import com.dailynuts.post.repository.CommentRepository;
import com.dailynuts.post.repository.PostLikeRepository;
import com.dailynuts.post.repository.PostRepository;
import com.dailynuts.post.service.mapper.PostMapper;
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
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostsServiceImpl implements PostsService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final PostMapper postMapper;
    private final SubscriptionRepository subscriptionRepository;

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

        List<PostResponseDto> postResponseDtoList = getPostResponseDtos(memberId, postPage);
        return new PostsResponseDto(
                postResponseDtoList,
                postPage.getTotalPages(),
                postPage.getNumber()
        );
    }

    @Override
    public PostsResponseDto getSubscribedFeed(Long memberId, Long categoryId, int page, int size, String criteria) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, criteria));
        List<Long> expertIds = subscriptionRepository.findExpertIdsBySubscriberId(memberId);

        if (expertIds.isEmpty()) {
            return new PostsResponseDto(List.of(), 0, page);
        }

        Page<Post> postPage = (categoryId == null || categoryId == 0L)
                ? postRepository.findByMember_IdIn(expertIds, pageable)
                : postRepository.findByMember_IdInAndCategory_Id(expertIds, categoryId, pageable);

        List<PostResponseDto> postResponseDtoList = getPostResponseDtos(memberId, postPage);
        return new PostsResponseDto(
                postResponseDtoList,
                postPage.getTotalPages(),
                postPage.getNumber()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PostsResponseDto getLikedPosts(Long memberId, Long categoryId, int pageNo, int size, String criteria) {
        Set<String> allowedCriteria = Set.of("createdAt", "likeCount", "commentCount");
        if (!allowedCriteria.contains(criteria)) {
            throw new CustomException(CustomErrorCode.INVALID_SORT_CRITERIA);
        }

        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, criteria));

        Page<Post> postPage;
        if (categoryId == null || categoryId == 0L) {
            postPage = postLikeRepository.findLikedPosts(memberId, pageable);
        } else {
            postPage = postLikeRepository.findLikedPostsByCategory(memberId, categoryId, pageable);
        }

        List<PostResponseDto> postResponseDtoList = getPostResponseDtos(memberId, postPage);
        return new PostsResponseDto(
                postResponseDtoList,
                postPage.getTotalPages(),
                postPage.getNumber()
        );
    }

    private List<PostResponseDto> getPostResponseDtos(Long memberId, Page<Post> postPage) {
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for (Post post : postPage.getContent()) {
            PostResponseDto response = toPostResponseDto(post, memberId);
            postResponseDtoList.add(response);
        }
        return postResponseDtoList;
    }

    private PostResponseDto toPostResponseDto(Post post, Long memberId) {
        boolean isExist = postLikeRepository.existsPostLikeByPostIdAndMemberId(post.getId(), memberId);
        int commentCount = commentRepository.countByPostId(post.getId());
        return postMapper.getPostResponseDto(post, isExist, commentCount);
    }

}
