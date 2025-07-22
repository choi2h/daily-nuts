package com.dailynuts.post.service;

import com.dailynuts.post.dto.PostLikesResponseDto;
import com.dailynuts.post.dto.PostsResponseDto;

public interface PostsService {
    PostsResponseDto getPosts(Long categoryId, int pageNo, int size, String criteria, Long memberId);

    PostsResponseDto getSubscribedFeed(Long memberId, Long categoryId, int page, int size, String criteria);

    PostsResponseDto getLikedPosts(Long memberId, Long categoryId, int pageNo, int size, String criteria);
}
