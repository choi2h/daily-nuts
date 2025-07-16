package com.dailynuts.post.repository;

import com.dailynuts.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsPostLikeByPostIdAndMemberId(Long postId, Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE PostLike WHERE postId=:postId AND memberId=:memberId")
    int deleteByPostIdAndMemberId(Long postId, Long memberId);
}
