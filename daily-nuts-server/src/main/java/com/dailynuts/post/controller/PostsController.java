package com.dailynuts.post.controller;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.post.dto.PostRequestDto;
import com.dailynuts.post.dto.PostResponseDto;
import com.dailynuts.post.service.PostService;
import com.dailynuts.security.jwt.JwtMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostsController {

    private final PostService postService;

    @PreAuthorize("hasRole('EXPERT')")
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(
            @RequestBody PostRequestDto requestDto,
            @AuthenticationPrincipal JwtMember userDetails)
    {
        if (userDetails == null) {
            throw new CustomException(CustomErrorCode.TOKEN_NOT_VALID);
        }

        PostResponseDto responseDto = postService.createPost(requestDto, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(
            @PathVariable Long id,
            @AuthenticationPrincipal JwtMember userDetails)
    {
        if (userDetails == null) {
            throw new CustomException(CustomErrorCode.TOKEN_NOT_VALID);
        }

        PostResponseDto responseDto = postService.getPost(id, userDetails);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long id, @RequestBody PostRequestDto requestDto,
            @AuthenticationPrincipal JwtMember userDetails)
    {
        if (userDetails == null) {
            throw new CustomException(CustomErrorCode.TOKEN_NOT_VALID);
        }

        PostResponseDto responseDto = postService.updatePost(id, requestDto, userDetails);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id,
            @AuthenticationPrincipal JwtMember userDetails)
    {
        if (userDetails == null) {
            throw new CustomException(CustomErrorCode.TOKEN_NOT_VALID);
        }

        postService.deletePost(id, userDetails);
        return ResponseEntity.noContent().build();
    }
}