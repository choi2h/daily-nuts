package com.dailynuts.member.service;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.member.dto.ExpertInfoRequestDto;
import com.dailynuts.member.dto.ExpertInfoResponseDto;
import com.dailynuts.member.entity.ExpertCertificationImage;
import com.dailynuts.member.entity.ExpertInfo;
import com.dailynuts.member.entity.Image;
import com.dailynuts.member.entity.Member;
import com.dailynuts.member.entity.type.ImageType;
import com.dailynuts.member.repository.ExpertInfoRepository;
import com.dailynuts.member.repository.ImageRepository;
import com.dailynuts.member.repository.MemberRepository;
import com.dailynuts.member.service.mapper.ExpertInfoMapper;
import com.dailynuts.security.jwt.JwtMember;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ExpertInfoServiceImpl implements ExpertInfoService {

    private final ExpertInfoRepository expertInfoRepository;
    private final ImageRepository imageRepository;
    private final FileService fileService;
    private final ExpertInfoMapper mapper;
    private final MemberRepository memberRepository;

    @Override
    public ExpertInfoResponseDto createExpertInfo(ExpertInfoRequestDto request, List<MultipartFile> files, JwtMember memberInfo) {
        log.debug("Create expert info to {}. request={}, fileCount={}", memberInfo.getLoginId(), request, files.size());
        if(expertInfoRepository.existsByMember_Id(memberInfo.getId())) {
            throw new CustomException(CustomErrorCode.EXPERT_ALREADY_EXIST);
        }

        Member member = memberRepository.findByLoginId(memberInfo.getLoginId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));

        ExpertInfo expertInfo = new ExpertInfo(member, request.getDescription());
        List<Image> images = getImagesAndSetToExpertInfo(files, expertInfo, memberInfo.getLoginId());
        expertInfoRepository.save(expertInfo);

        return mapper.toExpertInfoResponseDto(expertInfo, images);
    }

    @Override
    public ExpertInfoResponseDto getExpertInfo(Long memberId) {
        ExpertInfo expertInfo = expertInfoRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.EXPERT_NOT_EXIST));

        List<Image> images = imageRepository.findByExpertId(expertInfo.getId());

        return mapper.toExpertInfoResponseDto(expertInfo, images);
    }

    @Override
    public ExpertInfoResponseDto updateExpertInfo(ExpertInfoRequestDto request, List<MultipartFile> files, JwtMember memberInfo) {
        log.debug("Update expert info to {}. request={}, fileCount={}", memberInfo.getLoginId(), request, files.size());
        ExpertInfo expertInfo = expertInfoRepository.findByMember_Id(memberInfo.getId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.EXPERT_NOT_EXIST));

        expertInfo.updateDescription(request.getDescription());
        expertInfo.clearImages();
        List<Image> images = getImagesAndSetToExpertInfo(files, expertInfo, memberInfo.getLoginId());

        return mapper.toExpertInfoResponseDto(expertInfo, images);
    }

    private List<Image> getImagesAndSetToExpertInfo(List<MultipartFile> files, ExpertInfo expertInfo, String loginId) {
        List<Image> images = new ArrayList<>();
        files.forEach(file -> {
            String url = fileService.createFile(loginId, file);
            String name = file.getOriginalFilename();
            Image image = new Image(name, url, ImageType.CERTIFICATION);
            ExpertCertificationImage expertCertificationImage = new ExpertCertificationImage();
            expertCertificationImage.setImage(image);
            expertInfo.addImage(expertCertificationImage);

            images.add(image);
        });

        return images;
    }
}
