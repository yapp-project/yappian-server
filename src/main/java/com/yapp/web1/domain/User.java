package com.yapp.web1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yapp.web1.converter.UserRoleAttributeConverter;
import com.yapp.web1.domain.VO.UserRole;
import lombok.*;

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
@EqualsAndHashCode(exclude = {"userOrders", "readList", "commentList", "favorites", "joinedProjects"})
public class User extends BaseEntity {

    /** User Table Fields **/
    @Column(name="email", nullable=false, unique=true)
    private String email;

    @Column(name="name", nullable=false)
    private String name;

    @Column(name="user_role", nullable=false)
    @Convert(converter = UserRoleAttributeConverter.class)
    private UserRole role = UserRole.USER;

    /** Relation Mapping **/
    /** User - UserOrders 양방향 매핑 **/
    @JsonIgnore
    @OneToMany(mappedBy="user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<UserOrders> userOrders = new HashSet<>();

    /** User - Read 양방향 매핑 **/
    @JsonIgnore
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY, // EAGER
            orphanRemoval = true)
    private List<Read> readList = new ArrayList<>();

    /** User - Comment 양방향 매핑 **/
    @JsonIgnore
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    /** Relation Mapping - Join Table **/
    /** User - Project 단방향 매핑 **/
    @OneToMany(fetch=FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinTable(name="favorites",
            joinColumns = @JoinColumn(name="user_idx"),
            inverseJoinColumns = @JoinColumn(name="project_idx"))
    @OrderBy("project_idx desc")
    private Set<Project> favorites = new HashSet<>();

    /** User - Project 양방향 매핑 **/
    @ManyToMany(fetch=FetchType.LAZY,
            cascade = CascadeType.PERSIST)
    @JoinTable(name="joined",
            joinColumns = @JoinColumn(name="user_idx"),
            inverseJoinColumns = @JoinColumn(name="project_idx"))
    private Set<Project> joinedProjects = new HashSet<>();


    /** Method **/
    @Builder
    public User(String email, String name, UserRole role,
                Set<UserOrders> userOrders, List<Read> readList, List<Comment> commentList, Set<Project> favorites, Set<Project> joinedProject){
        this.email = email;
        this.name = name;
        this.role = role;

        this.userOrders = Optional.ofNullable(userOrders).orElse(this.userOrders);
        this.readList = Optional.ofNullable(readList).orElse(this.readList);
        this.commentList = Optional.ofNullable(commentList).orElse(this.commentList);
        this.favorites = Optional.ofNullable(favorites).orElse(this.favorites);
        this.joinedProjects = Optional.ofNullable(joinedProject).orElse(this.joinedProjects);
    }

}
