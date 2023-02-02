package com.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"username","email"}))
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;
    private String password;
    private String email;
    private boolean is_enabled;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String avatar;

    @CreatedDate
    private Date createddate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Collection<RoleEntity> roles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<PostEntity> postEntityList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<CommentEntity> commentEntityList;

    @ManyToMany(mappedBy = "userLikePost")
    private List<PostEntity> postLiked;

    public UserEntity(){
        this.is_enabled = true;
    }

    public boolean hasRole(String roleName){
        Iterator<RoleEntity> iterator = this.roles.iterator();
        while (iterator.hasNext()){
            RoleEntity role = iterator.next();
            if(role.getName() == roleName){
                return true;
            }
        }
        return false;
    }
}
