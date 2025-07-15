package com.dailynuts.post.controller;

import com.dailynuts.post.dto.PostLikeResponseDto;
import com.dailynuts.post.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post/{postId}/like")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping
    public ResponseEntity<PostLikeResponseDto> createPostLike(@PathVariable("postId")Long postId) {
        PostLikeResponseDto responseDto = postLikeService.createPostLike(postId, 1L);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @DeleteMapping
    public ResponseEntity<PostLikeResponseDto> deletePostLike(@PathVariable("postId")Long postId) {
        PostLikeResponseDto responseDto = postLikeService.deletePostLike(postId, 1L);
        return ResponseEntity.ok(responseDto);
    }
}
