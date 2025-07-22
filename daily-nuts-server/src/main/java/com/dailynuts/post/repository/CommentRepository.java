package com.dailynuts.post.repository;

import com.dailynuts.post.entity.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends MongoRepository<Comment,ObjectId> {
    Optional<Comment> findById(ObjectId id);

    //부모댓글
    List<Comment> findByParentCommentId(ObjectId parentCommentId);

    // 부모 댓글(대댓글이 아닌) 조회, 생성일 순 정렬
    List<Comment> findByPostIdAndParentCommentIdIsNullOrderByCreatedAtAsc(Long postId);

    int countByPostId(Long postId);
}
