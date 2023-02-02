package com.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name = "posts", uniqueConstraints = @UniqueConstraint(columnNames = {"title"}))
@EntityListeners(AuditingEntityListener.class)
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

    @Column(columnDefinition = "TEXT")
    private String shortdescription;

    @Column(columnDefinition = "TEXT")
    private String content;

    private boolean is_enabled;

    @CreatedDate
    private Date createddate;

    @LastModifiedDate
    private Date modifieddate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<CommentEntity> commentEntityList;

    public PostEntity(){
        this.is_enabled = true;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_like_posts", joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "post_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"))
    private List<UserEntity> userLikePost;

}
