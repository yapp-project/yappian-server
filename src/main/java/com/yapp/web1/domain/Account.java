package com.yapp.web1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yapp.web1.converter.AccountRoleAttributeConverter;
import com.yapp.web1.domain.VO.AccountRole;
import com.yapp.web1.social.AccountConnection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Account 테이블의 Domain 클래스
 *
 * @author Dakyung Ko
 * @author Jihye Kim
 */
@Entity
@Table(name="account")
@AttributeOverride(name="idx", column=@Column(name="account_idx"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
//@EqualsAndHashCode(exclude = {"accountOrders", "readList", "commentList", "favorites", "joinedProjects"})
public class Account extends BaseEntity {
    // TODO domain 객체 equal, hashcode, toString

    /** Account Table Fields **/
    @Column(name="email", nullable=false, unique=true)
    private String email;

    @Column(name="name", nullable=false)
    private String name;

    @Column(name="account_role", nullable=false)
    @Convert(converter = AccountRoleAttributeConverter.class)
    private AccountRole role = AccountRole.USER;


    /** Relation Mapping **/
    /** Account - AccountConnection 단방향 매핑 **/
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "provider_idx", referencedColumnName = "provider_idx",
            nullable = false, updatable = false, unique = true)
    private AccountConnection social;

    /** Account - AccountOrders 양방향 매핑 **/
    @JsonIgnore
    @OneToMany(mappedBy="account",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<AccountOrders> accountOrders = new HashSet<>();

    /** Relation Mapping - Join Table **/
    /** Account - Project 양방향 매핑 **/
    @ManyToMany(fetch=FetchType.LAZY,
            cascade = CascadeType.PERSIST)
    @JoinTable(name="joined",
            joinColumns = @JoinColumn(name="account_idx"),
            inverseJoinColumns = @JoinColumn(name="project_idx"))
    private Set<Project> joinedProjects = new HashSet<>();


    /** Method **/
    @Builder
    public Account(String email, String name, AccountRole role, AccountConnection social,
                   Set<AccountOrders> accountOrders, Set<Project> joinedProject){
        this.email = email;
        this.name = name;
        this.role = role;
        this.social = social;

        this.accountOrders = Optional.ofNullable(accountOrders).orElse(this.accountOrders);
        this.joinedProjects = Optional.ofNullable(joinedProject).orElse(this.joinedProjects);
    }

    // setter - joinedProjects
    public void setJoinedProjects(Set<Project> joinedProjects){
        this.joinedProjects = joinedProjects;
    }

    public static Account signUp(AccountConnection accountConnection) {
        return Account.builder()
                .email(accountConnection.getEmail())
                .name(accountConnection.getDisplayName())
                .role(AccountRole.USER)
                .social(accountConnection)
                .build();
    }
}
