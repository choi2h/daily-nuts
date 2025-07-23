package com.dailynuts.member.service.mapper;

import com.dailynuts.member.dto.ExpertInfoResponseDto;
import com.dailynuts.member.entity.ExpertInfo;
import com.dailynuts.member.entity.Image;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExpertInfoMapper {

    public ExpertInfoResponseDto toExpertInfoResponseDto(ExpertInfo expertInfo, List<Image> images) {
        ExpertInfoResponseDto response = new ExpertInfoResponseDto(expertInfo.getDescription());
        images.forEach(image -> response.addImage(image.getName(), image.getUrl()));
        return response;
    }


}
