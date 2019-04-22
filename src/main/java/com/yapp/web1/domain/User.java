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
//@EqualsAndHashCode(exclude = {"userOrders", "readList", "commentList", "favorites", "joinedProjects"})
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

    /** Relation Mapping - Join Table **/

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
                Set<UserOrders> userOrders, Set<Project> joinedProject){
        this.email = email;
        this.name = name;
        this.role = role;

        this.userOrders = Optional.ofNullable(userOrders).orElse(this.userOrders);
        this.joinedProjects = Optional.ofNullable(joinedProject).orElse(this.joinedProjects);
    }
    
    //    + hashcode/toString override
}
