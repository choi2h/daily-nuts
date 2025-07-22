package com.dailynuts.member.service;

import com.dailynuts.member.dto.ExpertInfoRequestDto;
import com.dailynuts.member.dto.ExpertInfoResponseDto;
import com.dailynuts.member.dto.ExpertProfileResponseDto;
import com.dailynuts.security.jwt.JwtMember;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExpertInfoService {

    ExpertInfoResponseDto createExpertInfo(ExpertInfoRequestDto request, List<MultipartFile> files, JwtMember memberInfo);

    ExpertInfoResponseDto getExpertInfo(Long memberId);

    ExpertInfoResponseDto updateExpertInfo(ExpertInfoRequestDto request, List<MultipartFile> files, JwtMember memberInfo);

    ExpertProfileResponseDto getExpertProfile(Long expertId, Long requesterId);
}
