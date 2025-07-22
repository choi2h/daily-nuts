package com.dailynuts.post.repository;

import com.dailynuts.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT member.id FROM Post WHERE id=:postId")
    Long findWriterMemberIdByPostId(Long postId);

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

    Page<Post> findByIsPinnedTrue(Pageable pageable);

    Page<Post> findByCategory_IdAndIsPinnedTrue(Long categoryId, Pageable pageable);

    Page<Post> findByMember_IdIn(List<Long> memberIds, Pageable pageable);

    Page<Post> findByMember_IdInAndCategory_Id(List<Long> memberIds, Long categoryId, Pageable pageable);

    List<Post> findByMember_IdAndIsPinnedTrue(Long memberId);

    List<Post> findByMember_IdAndIsPinnedFalse(Long memberId);
}
