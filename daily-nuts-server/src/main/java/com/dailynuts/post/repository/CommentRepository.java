package com.dailynuts.post.repository;

import com.dailynuts.post.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment,String> {
    List<Comment> findByPostIdOrderByCreatedAt(Long postId);

    //부모댓글
    List<Comment> findByParentCommentId(String parentCommentId);

    // 부모 댓글(대댓글이 아닌) 조회, 생성일 순 정렬
    List<Comment> findByPostIdAndParentCommentIdIsNullOrderByCreatedAtAsc(Long postId);
}
