package com.dailynuts.member.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SubscribeExpertsResponseDto {
    private List<SubscribeExpertResponseDto> experts;

    public SubscribeExpertsResponseDto() {
        this.experts = new ArrayList<>();
    }

    public void addExpert(SubscribeExpertResponseDto expert) {
        experts.add(expert);
    }
}
