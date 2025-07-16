package com.dailynuts.post.service;

import com.dailynuts.post.dto.PostLikeResponseDto;

public interface PostLikeService {

    PostLikeResponseDto createPostLike(Long postId, Long memberId);

    PostLikeResponseDto deletePostLike(Long postId, Long memberId);
}
