package com.dailynuts.post.service;

import com.dailynuts.post.dto.PostRequestDto;
import com.dailynuts.post.dto.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    PostResponseDto createPost(PostRequestDto request);
    PostResponseDto getPost(Long id);
    //List<PostResponseDto> getAllPosts();
    PostResponseDto updatePost(Long id, PostRequestDto request);
    void deletePost(Long id);
    Page<PostResponseDto> getPosts(Long categoryId, int pageNo, int size, String criteria);
}
