package com.dailynuts.post.service;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.post.dto.CommentRequestDto;
import com.dailynuts.post.dto.CommentResponseDto;
import com.dailynuts.post.dto.CommentsResponseDto;
import com.dailynuts.post.dto.DeleteResponseDto;
import com.dailynuts.post.entity.Comment;
import com.dailynuts.post.repository.CommentRepository;
import com.dailynuts.post.service.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    //댓글 등록
    @Override
    public CommentResponseDto createComment(Long postId, Long memberId, String writer, CommentRequestDto request) {
        validateContentLength(request.getContents());
        Comment comment = commentMapper.toEntity(request, postId, memberId, writer, null);
        Comment saved = commentRepository.save(comment);

        System.out.println(">>> 저장된 Comment의 commentId = " + saved.getId());

        return commentMapper.toResponse(saved);
    }

    //대댓글
    @Override
    public CommentResponseDto createReplyToComment(Long postId, ObjectId parentCommentId, Long memberId, String writer, CommentRequestDto request) {
        validateContentLength(request.getContents());

        // 부모 댓글 유효성 확인
        Comment parent = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.COMMENT_PARENT_NOT_FOUND));

        // 대댓글의 대댓글 방지
        if (parent.getParentCommentId() != null) {
            throw new CustomException(CustomErrorCode.COMMENT_REPLY_NOT_ALLOWED);
        }

        Comment reply = commentMapper.toEntity(request, postId, memberId, writer, parentCommentId);
        Comment saved = commentRepository.save(reply);
        return commentMapper.toResponse(saved);
    }

    //댓글+대댓글 조회
    @Override
    public CommentsResponseDto getCommentsByPostId(Long postId) {
        List<Comment> parentComments = commentRepository.findByPostIdAndParentCommentIdIsNullOrderByCreatedAtAsc(postId);

        List<CommentResponseDto> responseList = new ArrayList<>();

        for (Comment parent : parentComments) {
            log.info(">> 부모 댓글 ID = {}", parent.getId());

            List<Comment> replies = commentRepository.findByParentCommentId(parent.getId());
            log.info(">> 대댓글 수 = {}", replies.size());

            CommentResponseDto parentResponse = commentMapper.toResponse(parent);
            List<CommentResponseDto> replyResponses = replies.stream()
                    .map(commentMapper::toResponse)
                    .collect(Collectors.toList());
            parentResponse.setReplies(replyResponses);
            responseList.add(parentResponse);
        }
        return commentMapper.toResponseList(responseList);
    }

    //글자수제한
    private void validateContentLength(String contents) {
        if (contents == null || contents.length() < 1 || contents.length() > 100) {
            throw new CustomException(CustomErrorCode.COMMENT_CONTENT_INVALID);
        }
    }

    //댓글 수정
    @Override
    public CommentResponseDto updateComment(Long postId, String id, Long memberId, CommentRequestDto request){
        Comment comment = commentRepository.findById(new ObjectId(id))
                .orElseThrow(()-> new CustomException(CustomErrorCode.COMMENT_UNAUTHORIZED));

        if(!comment.getMemberId().equals(memberId)){
            throw  new CustomException(CustomErrorCode.COMMENT_UNAUTHORIZED);
        }

        validateContentLength(request.getContents());

        comment.setContents(request.getContents());
        comment.setUpdatedAt(LocalDateTime.now());
        Comment updated = commentRepository.save(comment);

        return commentMapper.toResponse(updated);
    }

    //댓글 삭제
    @Override
    public DeleteResponseDto deleteComment(ObjectId commentId, Long memberId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.COMMENT_NOT_FOUND));

        // 작성자 확인
        if (!comment.getMemberId().equals(memberId)) {
            throw new CustomException(CustomErrorCode.COMMENT_DELETE_UNAUTHORIZED);
        }

        // 부모 댓글이면 대댓글도 같이 삭제
        if (comment.getParentCommentId() == null) {
            List<Comment> replies = commentRepository.findByParentCommentId(commentId);
            commentRepository.deleteAll(replies);
        }

        // 댓글 삭제
        commentRepository.delete(comment);

        return new DeleteResponseDto("댓글 삭제 성공", commentId.toHexString());
    }
}