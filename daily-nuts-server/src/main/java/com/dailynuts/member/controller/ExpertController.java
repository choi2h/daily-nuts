package com.dailynuts.member.controller;

import com.dailynuts.member.dto.ExpertInfoRequestDto;
import com.dailynuts.member.dto.ExpertInfoResponseDto;
import com.dailynuts.member.service.ExpertInfoService;
import com.dailynuts.security.jwt.JwtMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/member/expert")
@RequiredArgsConstructor
public class ExpertController {

    private final ExpertInfoService expertInfoService;

    @PostMapping
    public ResponseEntity<ExpertInfoResponseDto> createExpertInfo(
            @RequestPart(name = "info") ExpertInfoRequestDto expertInfoRequest,
            @RequestPart(name = "files", required = false) List<MultipartFile> files,
            @AuthenticationPrincipal JwtMember memberInfo) {
        ExpertInfoResponseDto response = expertInfoService.createExpertInfo(expertInfoRequest, files, memberInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ExpertInfoResponseDto> getAllExpertInfo(
            @AuthenticationPrincipal JwtMember memberInfo
    ) {
        ExpertInfoResponseDto response = expertInfoService.getExpertInfo(memberInfo.getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ExpertInfoResponseDto> updateExpertInfo(
            @RequestPart(name = "info") ExpertInfoRequestDto expertInfoRequest,
            @RequestPart(name = "files", required = false) List<MultipartFile> files,
            @AuthenticationPrincipal JwtMember memberInfo) {
        ExpertInfoResponseDto response = expertInfoService.updateExpertInfo(expertInfoRequest, files, memberInfo);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
