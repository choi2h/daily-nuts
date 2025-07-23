package com.dailynuts.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class ExpertSearchResponseDto {
    private List<ExpertSearchDto> experts;
}
