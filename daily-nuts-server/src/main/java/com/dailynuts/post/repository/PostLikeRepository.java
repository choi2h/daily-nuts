package com.dailynuts.post.repository;

import com.dailynuts.post.entity.Post;
import com.dailynuts.post.entity.PostLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsPostLikeByPostIdAndMemberId(Long postId, Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE PostLike WHERE postId=:postId AND memberId=:memberId")
    int deleteByPostIdAndMemberId(Long postId, Long memberId);

    @Query("SELECT p FROM Post p JOIN PostLike pl ON p.id = pl.postId WHERE pl.memberId = :memberId")
    Page<Post> findLikedPosts(@Param("memberId") Long memberId, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN PostLike pl ON p.id = pl.postId WHERE pl.memberId = :memberId AND p.category.id = :categoryId")
    Page<Post> findLikedPostsByCategory(@Param("memberId") Long memberId, @Param("categoryId") Long categoryId, Pageable pageable);

    @Query("SELECT COUNT(pl) FROM PostLike pl WHERE pl.postId = :postId")
    int countByPostId(@Param("postId") Long postId);
}
