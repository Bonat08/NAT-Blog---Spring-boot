package com.blog.service.impl;

import com.blog.dto.PostDto;
import com.blog.entity.CategoryEntity;
import com.blog.entity.PostEntity;
import com.blog.entity.UserEntity;
import com.blog.repository.CategoryRepository;
import com.blog.repository.PostRepository;
import com.blog.repository.UserRepository;
import com.blog.service.PostService;
import com.blog.utils.ImageUpload;
import com.blog.utils.PostConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private ImageUpload imageUpload;

    @Autowired
    private PostConverter postConverter;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PostDto save(MultipartFile imagePost, PostDto postDto) {
        try{
            PostEntity postEntity = postConverter.toEntity(postDto);

            if(imagePost == null){
                postEntity.setImage(null);
            }
            else {
                imageUpload.uploadImage(imagePost);
                postEntity.setImage(Base64.getEncoder().encodeToString(imagePost.getBytes()));
            }
            postEntity.set_enabled(true);
            return postConverter.toDto(postRepository.save(postEntity));

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private Page toPage(List<PostDto> list, Pageable pageable){
        if(pageable.getOffset() >= list.size()){
            return Page.empty();
        }
        int startIndex = (int)pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size()
                : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList,pageable, list.size());
    }

    @Override
    public Page<PostDto> pagePost(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<PostEntity> postEntities = postRepository.findAll();
        List<PostDto> postDtos = new ArrayList<>();
        for(PostEntity postEntity:postEntities){
            postDtos.add(postConverter.toDto(postEntity));
        }
        Page<PostDto> pagePosts = toPage(postDtos,pageable);
        return pagePosts;
    }

    @Override
    public Page<PostDto> pagePostOfUser(int pageNo, String username) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        UserEntity user = userRepository.findByUsername(username);
        List<PostEntity> postEntities = postRepository.findAllByUser(user);
        List<PostDto> postDtos = new ArrayList<>();
        for(PostEntity postEntity:postEntities){
            postDtos.add(postConverter.toDto(postEntity));
        }
        Page<PostDto> pagePosts = toPage(postDtos,pageable);
        return pagePosts;
    }

    @Override
    public Page<PostDto> pagePostEnabled(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<PostEntity> postEntities = postRepository.findAllEnabled();
        List<PostDto> postDtos = new ArrayList<>();
        for(PostEntity postEntity:postEntities){
            postDtos.add(postConverter.toDto(postEntity));
        }
        Page<PostDto> pagePosts = toPage(postDtos,pageable);
        return pagePosts;
    }

    @Override
    public Page<PostDto> pagePostCategoryAndEnabled(int pageNo, String category) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        CategoryEntity categoryEntity = categoryRepository.findOneByName(category);
        List<PostEntity> postEntities = postRepository.findAllByCategory(categoryEntity);
        List<PostDto> postDtos = new ArrayList<>();
        for(PostEntity postEntity:postEntities){
            if(postEntity.is_enabled() == true){
                postDtos.add(postConverter.toDto(postEntity));
            }
        }
        Page<PostDto> pagePosts = toPage(postDtos,pageable);
        return pagePosts;
    }

    @Override
    public List<PostDto> selectTop5ByDate() {
        List<PostEntity> postEntities = postRepository.selectTop5ByDate();
        List<PostDto> postDtos = new ArrayList<>();
        for(PostEntity postEntity:postEntities){
            postDtos.add(postConverter.toDto(postEntity));
        }
        return postDtos;
    }

    @Override
    public PostDto update(MultipartFile imagePost, PostDto postDto) {
        try{
            PostEntity postOldEntity = postRepository.findById(postDto.getId()).get();
            PostEntity postEntity = postConverter.toEntity(postOldEntity,postDto);

            if(imagePost.isEmpty()){
                postEntity.setImage(postEntity.getImage());
            }
            else {
                if(imageUpload.checkExisted(imagePost) == false){
                    imageUpload.uploadImage(imagePost);
                }
                postEntity.setImage(Base64.getEncoder().encodeToString(imagePost.getBytes()));
            }
            return postConverter.toDto(postRepository.save(postEntity));

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PostDto findById(Long id) {
        return postConverter.toDto(postRepository.findById(id).get());
    }

    @Override
    public void enabledById(Long id) {
        PostEntity postEntity = postRepository.findById(id).get();
        postEntity.set_enabled(true);
        postRepository.save(postEntity);
    }

    @Override
    public void disabledById(Long id) {
        PostEntity postEntity = postRepository.findById(id).get();
        postEntity.set_enabled(false);
        postRepository.save(postEntity);
    }

    @Override
    public int countPost() {
        return postRepository.countPost();
    }

    @Override
    public PostDto selectRandom() {
        return postConverter.toDto(postRepository.selectRandom());
    }
}
