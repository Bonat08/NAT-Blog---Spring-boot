package com.blog.repository;

import com.blog.entity.CommentEntity;
import com.blog.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByPost(PostEntity post);

    Long countAllByPost(PostEntity post);
}
