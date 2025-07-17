package com.dailynuts.post.repository;

import com.dailynuts.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT member.id FROM Post WHERE id=:postId")
    Long findMemberIdByPostId(Long postId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Post SET likeCount = likeCount+1 WHERE id=:postId")
    void increaseLikeCount(Long postId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Post SET likeCount = likeCount-1 WHERE id=:postId AND likeCount > 0")
    void decreaseLikeCount(Long postId);

    @Query("SELECT likeCount FROM Post WHERE id=:postId")
    int findLikeCountById(Long postId);

    Page<Post> findByCategory_Id(Long categoryId, Pageable pageable);

    long countByMemberId(Long memberId);

}
