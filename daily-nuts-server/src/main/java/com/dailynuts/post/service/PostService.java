package com.dailynuts.post.service;

import com.dailynuts.post.dto.PostRequestDto;
import com.dailynuts.post.dto.PostResponseDto;
import com.dailynuts.post.dto.PostsResponseDto;
import com.dailynuts.security.jwt.JwtMember;

public interface PostService {
    PostResponseDto createPost(PostRequestDto request, JwtMember userDetails);
    PostResponseDto getPost(Long id, JwtMember userDetails);
    PostResponseDto updatePost(Long id, PostRequestDto request, JwtMember userDetails);
    void deletePost(Long id, JwtMember userDetails);
    PostsResponseDto getPosts(Long categoryId, int pageNo, int size, String criteria);
}
