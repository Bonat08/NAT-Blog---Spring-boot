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
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@EntityListeners(AuditingEntityListener.class)
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;
    private boolean is_enabled;

    @CreatedDate
    private Date createddate;

    @LastModifiedDate
    private Date modifieddate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<PostEntity> postEntityList;

    public CategoryEntity(){
        this.is_enabled=true;
    }

}
