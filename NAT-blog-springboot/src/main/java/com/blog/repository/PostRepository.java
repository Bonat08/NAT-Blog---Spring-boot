package com.blog.repository;

import com.blog.entity.CategoryEntity;
import com.blog.entity.PostEntity;
import com.blog.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query("select p from PostEntity p where p.is_enabled=true order by p.id desc ")
    List<PostEntity> findAllEnabled();

    @Query(value = "select * from posts p where p.is_enabled=true order by p.post_id desc limit 5", nativeQuery = true)
    List<PostEntity> selectTop5ByDate();

    @Query(value = "select * from posts p where p.is_enabled=true order by RAND() limit 1", nativeQuery = true)
    PostEntity selectRandom();

    @Query(value = "select count(*) from users_like_posts u where u.user_id = ?1", nativeQuery = true)
    int countLikes(Long id);

    @Query("select count(p) from PostEntity p")
    int countPost();

    @Query("select p from PostEntity p join p.category c where c.name like %?1% or p.title like %?1% and p.is_enabled=true order by p.id desc ")
    List<PostEntity> searchPostList(String keyword);

    List<PostEntity> findAllByUser(UserEntity user);

    List<PostEntity> findAllByCategory(CategoryEntity category);


}
