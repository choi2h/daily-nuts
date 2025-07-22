package com.dailynuts.member.controller;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.member.dto.ExpertInfoResponseDto;
import com.dailynuts.member.dto.ExpertSearchResponseDto;
import com.dailynuts.member.dto.SubscribeExpertsResponseDto;
import com.dailynuts.member.entity.Member;
import com.dailynuts.member.service.ExpertSearchService;
import com.dailynuts.security.jwt.JwtMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class ExpertSearchController {

    private final ExpertSearchService expertSearchService;

    @GetMapping("/search")
    public ResponseEntity<ExpertSearchResponseDto> searchExperts(@RequestParam(required = false) String name,
                                           @AuthenticationPrincipal JwtMember member) {
        if (name == null || name.isBlank()) {
            throw new CustomException(CustomErrorCode.SEARCH_KEYWORD_EMPTY);
        }

        Long subscriberId = (member != null) ? member.getId() : null;

        ExpertSearchResponseDto results = expertSearchService.searchExperts(name, subscriberId);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/subscribe")
    public ResponseEntity<SubscribeExpertsResponseDto> getSubscribeExperts(@AuthenticationPrincipal JwtMember memberInfo) {
        SubscribeExpertsResponseDto response = expertSearchService.subscribeExperts(memberInfo.getId());
        return ResponseEntity.ok(response);
    }
}

