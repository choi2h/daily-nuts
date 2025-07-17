package com.dailynuts.member.service;

import com.dailynuts.member.dto.ExpertSearchResponseDto;

public interface ExpertSearchService {
    ExpertSearchResponseDto searchExperts(String name, Long subscriberId);
}