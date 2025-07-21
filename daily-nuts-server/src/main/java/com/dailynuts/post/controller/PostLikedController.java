package com.dailynuts.post.controller;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.post.dto.PostLikesResponseDto;
import com.dailynuts.post.service.PostLikeService;
import com.dailynuts.security.jwt.JwtMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts/liked")
@RequiredArgsConstructor
public class PostLikedController {

    private final PostLikeService postLikeService;

    @GetMapping
    public ResponseEntity<PostLikesResponseDto> getLikedPosts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String criteria,
            @AuthenticationPrincipal JwtMember userDetails
    )
    {
        if (userDetails == null) {
            throw new CustomException(CustomErrorCode.TOKEN_NOT_VALID);
        }

        Long memberId = userDetails.getId();

        PostLikesResponseDto postLikesResponseDto = postLikeService.getLikedPosts(memberId, categoryId, page, size, criteria);
        return ResponseEntity.ok(postLikesResponseDto);
    }
}
