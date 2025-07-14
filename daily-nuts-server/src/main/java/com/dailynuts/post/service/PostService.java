package com.dailynuts.post.service;

import com.dailynuts.post.dto.PostRequestDto;
import com.dailynuts.post.dto.PostResponseDto;

import java.util.List;

public interface PostService {
    PostResponseDto createPost(PostRequestDto request);
    PostResponseDto getPost(Long id);
    List<PostResponseDto> getAllPosts();
    PostResponseDto updatePost(Long id, PostRequestDto request);
    void deletePost(Long id);
}
