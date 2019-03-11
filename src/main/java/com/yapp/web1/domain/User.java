package com.yapp.web1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yapp.web1.converter.UserRoleAttributeConverter;
import com.yapp.web1.domain.VO.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

/**
 * User 테이블의 Domain 클래스
 *
 * @author Dakyung Ko, Jihye Kim
 */

@Entity
@Table(name="user")
@AttributeOverride(name="idx", column=@Column(name="user_idx"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {

    @Column(name="email", nullable=false, unique=true)
    private String email;

    @Column(name="name", nullable=false)
    private String name;

    @Column(name="userRole", nullable=false)
    @Convert(converter = UserRoleAttributeConverter.class)
    private UserRole role = UserRole.USER;

    @JsonIgnore
    @OneToMany(mappedBy="user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<UserOrder> userOrders = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();


    @OneToMany(fetch=FetchType.LAZY,
            cascade = CascadeType.REMOVE,//ALL
            orphanRemoval = true)
    @JoinTable(name="favorite",
            joinColumns = @JoinColumn(name="user_idx"),
            inverseJoinColumns = @JoinColumn(name="project_idx"))
    @OrderBy("project_idx desc")
    private Set<Project> favorite = new HashSet<>();

    @OneToMany(fetch=FetchType.LAZY,
            cascade = CascadeType.PERSIST,
            orphanRemoval = true)
    @JoinTable(name="joined_project",
            joinColumns = @JoinColumn(name="user_idx"),
            inverseJoinColumns = @JoinColumn(name="project_idx"))
    private Set<Project> joinedProject = new HashSet<>();

    @Builder
    public User(String email, String name,
                Set<UserOrder> userOrders, List<Comment> commentList, Set<Project> favorite, Set<Project> joinedProject){
        this.email = email;
        this.name = name;

        this.userOrders = Optional.ofNullable(userOrders).orElse(this.userOrders);
        this.commentList = Optional.ofNullable(commentList).orElse(this.commentList);
        this.favorite = Optional.ofNullable(favorite).orElse(this.favorite);
        this.joinedProject = Optional.ofNullable(joinedProject).orElse(this.joinedProject);
    }

//    + hashcode/toString override
}
