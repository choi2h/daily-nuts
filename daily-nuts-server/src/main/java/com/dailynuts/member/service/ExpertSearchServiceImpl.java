package com.dailynuts.member.service;

import com.dailynuts.member.dto.ExpertSearchDto;
import com.dailynuts.member.dto.ExpertSearchResponseDto;
import com.dailynuts.member.dto.SubscribeExpertResponseDto;
import com.dailynuts.member.dto.SubscribeExpertsResponseDto;
import com.dailynuts.member.entity.ExpertInfo;
import com.dailynuts.member.entity.Member;
import com.dailynuts.member.entity.type.ImageType;
import com.dailynuts.member.entity.type.Role;
import com.dailynuts.member.repository.ImageRepository;
import com.dailynuts.member.repository.MemberRepository;
import com.dailynuts.member.service.mapper.ExpertSearchMapper;
import com.dailynuts.post.repository.PostRepository;
import com.dailynuts.subscription.entity.Subscription;
import com.dailynuts.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpertSearchServiceImpl implements ExpertSearchService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final ExpertSearchMapper expertSearchMapper;
    private final ImageRepository imageRepository;

    @Override
    public ExpertSearchResponseDto searchExperts(String name, Long subscriberId) {
        List<Member> experts = memberRepository.findByNameContainingAndRole(name, Role.EXPERT);

        List<ExpertSearchDto> dtoList = experts.stream()
                .map(expert -> {
                    ExpertInfo expertInfo = expert.getExpertInfo();
                    String profileImageUrl = expertSearchMapper.extractProfileImageUrl(expertInfo);
                    int postCount = (int) postRepository.countByMemberId(expert.getId());
                    boolean subscribed = subscriptionRepository.existsBySubscriberIdAndExpertId(subscriberId, expert.getId());

                    ExpertSearchDto dto =  expertSearchMapper.toExpertSearchDto(
                            expert,
                            profileImageUrl,
                            postCount,
                            subscribed
                    );

                    imageRepository.findByMemberIdAndType(expert.getId(), ImageType.PROFILE)
                            .ifPresent(image -> dto.setProfileImage(image.getName()));

                    return dto;
                })
                .collect(Collectors.toList());

        return new ExpertSearchResponseDto(dtoList);
    }

    @Override
    public SubscribeExpertsResponseDto subscribeExperts(Long memberId) {
        List<Subscription> subscribeExpertIds = subscriptionRepository.findAllBySubscriberId(memberId, true);
        SubscribeExpertsResponseDto response = new SubscribeExpertsResponseDto();
        for(Subscription subscription : subscribeExpertIds) {
            Optional<Member> memberOptional = memberRepository.findById(subscription.getExpertId());
            if (memberOptional.isEmpty()) continue;
            Member expert = memberOptional.get();
            SubscribeExpertResponseDto dto = SubscribeExpertResponseDto.builder()
                    .id(expert.getId())
                    .name(expert.getName())
                    .subscribeDate(subscription.getStartedAt().toLocalDate())
                    .build();

            imageRepository.findByMemberIdAndType(expert.getId(), ImageType.PROFILE)
                    .ifPresent(image -> dto.setProfileImage(image.getName()));

            response.addExpert(dto);
        }
        return response;
    }
}
