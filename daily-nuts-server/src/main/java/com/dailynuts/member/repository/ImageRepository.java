package com.dailynuts.member.repository;

import com.dailynuts.member.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query("SELECT i FROM Image i WHERE i.id IN ((SELECT ei.image.id FROM ExpertCertificationImage ei WHERE ei.expertInfo.id=:expertId ))")
    List<Image> findByExpertId(Long expertId);
}
