package com.dailynuts.member.service;

import com.dailynuts.member.dto.ExpertSearchDto;
import com.dailynuts.member.entity.ExpertInfo;
import com.dailynuts.member.entity.Member;
import com.dailynuts.member.entity.type.Role;
import com.dailynuts.member.repository.MemberRepository;
import com.dailynuts.member.service.mapper.ExpertSearchMapper;
import com.dailynuts.post.repository.PostRepository;
import com.dailynuts.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpertSearchServiceImpl implements ExpertSearchService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final ExpertSearchMapper expertSearchMapper;

    @Override
    public List<ExpertSearchDto> searchExperts(String name, Long subscriberId) {
        List<Member> experts = memberRepository.findByNameContainingAndRole(name, Role.EXPERT);

        return experts.stream()
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
    }
}
