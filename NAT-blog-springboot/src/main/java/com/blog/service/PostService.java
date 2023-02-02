package com.blog.service;

import com.blog.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface PostService {
    PostDto save(MultipartFile imagePost, PostDto postDto);
    Page<PostDto> pagePost(int pageNo);

    Page<PostDto> pagePostOfUser(int pageNo, String username);

    Page<PostDto> pagePostEnabled(int pageNo);

    Page<PostDto> searchPostList(int pageNo, String keyword);

    Page<PostDto> pagePostCategoryAndEnabled(int pageNo, String category);

    List<PostDto> selectTop5ByDate();

    PostDto update(MultipartFile imagePost, PostDto postDto);

    PostDto findById(Long id);

    void enabledById(Long id);

    void disabledById(Long id);

    int countPost();

    PostDto selectRandom();

    void likePost(Long id, String username);

    void unLikePost(Long id, String username);

    int countLikePost(Long id);

    String checkIfUserLikedPost(Long id, String username);
}
