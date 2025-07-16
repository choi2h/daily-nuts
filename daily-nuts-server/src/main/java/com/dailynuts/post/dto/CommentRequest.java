package com.dailynuts.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequest {

    @NotBlank(message = "내용을 입력해주세요")
    @Size(min=1, max = 100, message = "댓글은 100자 이하로 작성해주세요")
    private String contents;

    private String writer;
}
