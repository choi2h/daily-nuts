package com.dailynuts.post.service.mapper;

import com.dailynuts.member.entity.Member;
import com.dailynuts.post.dto.PostRequestDto;
import com.dailynuts.post.dto.PostResponseDto;
import com.dailynuts.post.entity.Category;
import com.dailynuts.post.entity.Post;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostMapper {
    public Post getPostEntity(PostRequestDto requestDto,
                              Member member, Category category)
    {
        return Post.builder()
                .member(member)
                .category(category)
                .title(requestDto.getTitle())
                .contents(requestDto.getContents())
                .isPinned(false)
                .likeCount(0)
                .build();
    }

    public PostResponseDto getPostResponseDto(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .writer(post.getWriter())
                .categoryName(post.getCategory().getName())
                .categoryId(post.getCategory().getId())
                .likeCount(post.getLikeCount())
                .isPinned(post.isPinned())
                .createdAt(post.getCreatedAt())
                .memberId(post.getMember().getId())
                .build();
    }

    public List<PostResponseDto> getPostResponseDtoList(List<Post> postList) {
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        for (Post post : postList) {
            PostResponseDto responseDto = getPostResponseDto(post);
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }
}
