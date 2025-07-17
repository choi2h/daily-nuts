package com.dailynuts.post.controller;

import com.dailynuts.post.dto.PostResponseDto;
import com.dailynuts.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

//    @GetMapping
//    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
//        List<PostResponseDto> responseDtoList = postService.getAllPosts();
//        return ResponseEntity.ok(responseDtoList);
//    }

    @GetMapping
    public Page<PostResponseDto> getPosts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "createdAt") String criteria)
    {
        return postService.getPosts(categoryId, page, criteria);
    }
}
