package com.dailynuts.post.service;

import com.dailynuts.post.dto.CommentRequest;
import com.dailynuts.post.dto.CommentResponse;
import com.dailynuts.post.entity.Comment;
import com.dailynuts.post.repository.CommentRepository;
import com.dailynuts.post.service.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    //댓글 등록
    @Override
    public CommentResponse createComment(Long postId, Long memberId, String writer, CommentRequest request) {
        validateContentLength(request.getContents());
        Comment comment = commentMapper.toEntity(request, postId, memberId, writer, null);
        Comment saved = commentRepository.save(comment);

        System.out.println(">>> 저장된 Comment의 commentId = " + saved.getId());

        return commentMapper.toResponse(saved);
    }


    //대댓글
    @Override
    public CommentResponse createReplyToComment(Long postId, ObjectId parentCommentId, Long memberId, String writer, CommentRequest request) {
        validateContentLength(request.getContents());

        // 부모 댓글 유효성 확인
        Comment parent = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 부모 댓글입니다."));

        // 대댓글의 대댓글 방지
        if (parent.getParentCommentId() != null) {
            throw new IllegalArgumentException("대댓글에는 다시 답글을 달 수 없습니다.");
        }

        Comment reply = commentMapper.toEntity(request, postId, memberId, writer, parentCommentId);
        Comment saved = commentRepository.save(reply);
        return commentMapper.toResponse(saved);
    }

    //댓글+대댓글 조회
    @Override
    public List<CommentResponse> getCommentsByPostId(Long postId) {
        List<Comment> parentComments = commentRepository.findByPostIdAndParentCommentIdIsNullOrderByCreatedAtAsc(postId);

        List<CommentResponse> responseList = new ArrayList<>();

        for (Comment parent : parentComments) {
            System.out.println(">> 부모 댓글 ID = " + parent.getId());
            List<Comment> replies = commentRepository.findByParentCommentId(parent.getId());
            System.out.println(">> 대댓글 수 = " + replies.size());
            CommentResponse parentResponse = commentMapper.toResponse(parent);
            List<CommentResponse> replyResponses = commentMapper.toResponseList(replies);
            parentResponse.setReplies(replyResponses);
            responseList.add(parentResponse);
        }
        return responseList;
    }

    //글자수제한
    private void validateContentLength(String contents) {
        if (contents == null || contents.length() < 1 || contents.length() > 100) {
            throw new IllegalArgumentException("댓글 내용은 1자 이상 100자 이하로 입력해주세요.");
        }
    }

    //댓글 수정
    @Override
    public CommentResponse updateComment(Long postId, String id, Long memberId, CommentRequest request){
        Comment comment = commentRepository.findById(new ObjectId(id))
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        if(!comment.getMemberId().equals(memberId)){
            throw  new IllegalArgumentException("댓글 작성자만 수정할 수 있습니다.");
        }

        validateContentLength(request.getContents());

        comment.setContents(request.getContents());
        comment.setUpdatedAt(LocalDateTime.now());
        Comment updated = commentRepository.save(comment);

        return commentMapper.toResponse(updated);
    }

}