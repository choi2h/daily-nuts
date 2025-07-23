package com.dailynuts.member.repository;

import com.dailynuts.member.entity.Image;
import com.dailynuts.member.entity.type.ImageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query("SELECT i FROM Image i WHERE i.id IN ((SELECT ei.image.id FROM ExpertCertificationImage ei WHERE ei.expertInfo.id=:expertId ))")
    List<Image> findByExpertId(Long expertId);

    @Query("SELECT i.url FROM Image i WHERE i.id IN (SELECT ei.image.id FROM ExpertCertificationImage ei WHERE ei.expertInfo.id=:expertId) AND i.type=:type")
    String findByExpertIdAndType(Long expertId, ImageType type);

    Optional<Image> findByMemberIdAndType(Long memberId, ImageType type);
}
