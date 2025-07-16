package com.dailynuts.post.service;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.member.entity.Member;
import com.dailynuts.member.repository.MemberRepository;
import com.dailynuts.post.dto.PostRequestDto;
import com.dailynuts.post.dto.PostResponseDto;
import com.dailynuts.post.entity.Category;
import com.dailynuts.post.entity.Post;
import com.dailynuts.post.repository.CategoryRepository;
import com.dailynuts.post.repository.PostRepository;
import com.dailynuts.post.service.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public PostResponseDto createPost(PostRequestDto request) {
        //테스트용 멤버
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));

        //테스트용 카테고리
        Category category = categoryRepository.findById(request.getCategoryId()).get();

        Post post = postMapper.getPostEntity(request, member, category);
        Post saved = postRepository.save(post);
        return postMapper.getPostResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomErrorCode.POST_NOT_FOUND));

        return postMapper.getPostResponseDto(post);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts() {
        List<Post> postList = postRepository.findAll();
        return postMapper.getPostResponseDtoList(postList);
    }

    @Override
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomErrorCode.POST_NOT_FOUND));

        //테스트용 카테고리
        Category category = categoryRepository.findById(request.getCategoryId()).get();

        post.update(request.getTitle(), request.getContents(), category);
        return postMapper.getPostResponseDto(post);
    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomErrorCode.POST_NOT_FOUND));

        postRepository.delete(post);
    }
}
