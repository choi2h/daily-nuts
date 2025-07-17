package com.dailynuts.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExpertSearchResponseDto {
    private List<ExpertSearchDto> experts;
}
