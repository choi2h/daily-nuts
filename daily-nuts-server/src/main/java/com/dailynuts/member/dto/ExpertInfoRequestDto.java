package com.dailynuts.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpertInfoRequestDto {
    @NotBlank
    @Size(min = 10, max = 200, message = "설명은 10자 이상 200자 내로 작성해주세요.")
    private String description;
}
