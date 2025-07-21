package com.dailynuts.member.service;

import com.dailynuts.member.dto.ExpertSearchResponseDto;
import com.dailynuts.member.dto.SubscribeExpertsResponseDto;

public interface ExpertSearchService {
    ExpertSearchResponseDto searchExperts(String name, Long subscriberId);
    SubscribeExpertsResponseDto subscribeExperts(Long memberId);
}