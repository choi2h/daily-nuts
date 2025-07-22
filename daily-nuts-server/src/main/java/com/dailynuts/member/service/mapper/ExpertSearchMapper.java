package com.dailynuts.member.service.mapper;

import com.dailynuts.member.dto.ExpertSearchDto;
import com.dailynuts.member.entity.ExpertCertificationImage;
import com.dailynuts.member.entity.ExpertInfo;
import com.dailynuts.member.entity.Image;
import com.dailynuts.member.entity.Member;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ExpertSearchMapper {

    public ExpertSearchDto toExpertSearchDto(Member expert, String profileImageUrl, int postCount, boolean subscribed) {
        return new ExpertSearchDto(
                expert.getId(),
                expert.getName(),
                profileImageUrl,
                postCount,
                subscribed
        );
    }

    public String extractProfileImageUrl(ExpertInfo expertInfo) {
        if (expertInfo != null && expertInfo.getImages() != null && !expertInfo.getImages().isEmpty()) {
            Optional<String> url = expertInfo.getImages().stream()
                    .map(ExpertCertificationImage::getImage)
                    .map(Image::getUrl)
                    .findFirst();
            return url.orElse(null);
        }
        return null;
    }
}