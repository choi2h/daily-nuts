package com.dailynuts.member.service;

import com.dailynuts.member.dto.ExpertSearchDto;

import java.util.List;

public interface ExpertSearchService {
    List<ExpertSearchDto> searchExperts(String name, Long subscriberId);
}