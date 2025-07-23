package com.dailynuts.post.controller;

import com.dailynuts.post.dto.PostsResponseDto;
import com.dailynuts.post.service.PostsService;
import com.dailynuts.security.jwt.JwtMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostsService postsService;

    @GetMapping
    public ResponseEntity<PostsResponseDto> getPosts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String criteria,
            @AuthenticationPrincipal JwtMember memberInfo)
    {
        PostsResponseDto responseDto = postsService.getPosts(categoryId, page, size, criteria, memberInfo.getId());
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/sub")
    public ResponseEntity<PostsResponseDto> getSubscribedFeed(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String criteria,
            @AuthenticationPrincipal JwtMember memberInfo)
    {
        PostsResponseDto response = postsService.getSubscribedFeed(memberInfo.getId(), categoryId, page, size, criteria);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/liked")
    public ResponseEntity<PostsResponseDto> getLikedPosts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String criteria,
            @AuthenticationPrincipal JwtMember userDetails
    )
    {
        PostsResponseDto response = postsService.getLikedPosts(userDetails.getId(), categoryId, page, size, criteria);
        return ResponseEntity.ok(response);
    }
}
