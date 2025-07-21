package com.dailynuts.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

    @NotBlank(message = "댓글 내용을 입력해주세요.")
    @Size(min = 1, max = 100, message = "댓글 내용은 1자 이상 100자 이하로 작성해야 합니다.")
    private String contents;
}
