package com.dailynuts.member.service;

import com.dailynuts.member.dto.ExpertSearchDto;
import com.dailynuts.member.dto.ExpertSearchResponseDto;
import com.dailynuts.member.dto.SubscribeExpertResponseDto;
import com.dailynuts.member.dto.SubscribeExpertsResponseDto;
import com.dailynuts.member.entity.ExpertInfo;
import com.dailynuts.member.entity.Image;
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

                    return expertSearchMapper.toExpertSearchDto(
                            expert,
                            profileImageUrl,
                            postCount,
                            subscribed
                    );
                })
                .collect(Collectors.toList());

        return new ExpertSearchResponseDto(dtoList);
    }

    @Override
    public SubscribeExpertsResponseDto subscribeExperts(Long memberId) {
        List<Subscription> subscribeExpertIds = subscriptionRepository.findAllBySubscriberId(memberId, true);
        SubscribeExpertsResponseDto response = new SubscribeExpertsResponseDto();
        for(Subscription subscription : subscribeExpertIds) {
            String imageUrl = imageRepository.findByExpertIdAndType(memberId, ImageType.PROFILE);
            Optional<Member> member = memberRepository.findById(subscription.getExpertId());
            if (member.isEmpty()) continue;
            SubscribeExpertResponseDto expert = SubscribeExpertResponseDto.builder()
                    .name(member.get().getName())
                    .profileImageUrl(imageUrl)
                    .subscribeDate(subscription.getStartedAt().toLocalDate())
                    .build();
            response.addExpert(expert);
        }
        return response;
    }
}
