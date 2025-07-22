package com.dailynuts.post.service;

import com.dailynuts.post.dto.PostLikeResponseDto;
import com.dailynuts.post.dto.PostLikesResponseDto;

public interface PostLikeService {

    PostLikeResponseDto createPostLike(Long postId, Long memberId);

    PostLikeResponseDto deletePostLike(Long postId, Long memberId);

    PostLikesResponseDto getLikedPosts(Long memberId, Long categoryId, int pageNo, int size, String criteria);
}
