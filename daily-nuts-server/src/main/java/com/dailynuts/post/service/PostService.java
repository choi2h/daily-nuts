package com.dailynuts.post.service;

import com.dailynuts.post.dto.PostRequestDto;
import com.dailynuts.post.dto.PostResponseDto;
import com.dailynuts.post.dto.PostsResponseDto;

public interface PostService {
    PostResponseDto createPost(PostRequestDto request);
    PostResponseDto getPost(Long id);
    //List<PostResponseDto> getAllPosts();
    PostResponseDto updatePost(Long id, PostRequestDto request);
    void deletePost(Long id);
    PostsResponseDto getPosts(Long categoryId, int pageNo, int size, String criteria);
}
