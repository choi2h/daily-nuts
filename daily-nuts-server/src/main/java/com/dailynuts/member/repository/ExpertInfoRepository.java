package com.dailynuts.member.repository;

import com.dailynuts.member.entity.ExpertInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpertInfoRepository extends JpaRepository<ExpertInfo, Long> {

    Optional<ExpertInfo> findByMember_Id(Long memberId);

    boolean existsByMember_Id(Long memberId);
}
