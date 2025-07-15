package com.dailynuts.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Builder
public class CommentListResponse {
    private List<CommentResponse> comments;

    public CommentListResponse(List<CommentResponse> comments) {
        this.comments = comments;
    }

    public List<CommentResponse> getComments() {
        return comments;
    }

    public void setComments(List<CommentResponse> comments) {
        this.comments = comments;
    }
}
