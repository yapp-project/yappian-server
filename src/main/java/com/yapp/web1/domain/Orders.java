package com.yapp.web1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Orders 테이블(프로젝트 기수)의 Domain 클래스
 *
 * @author Dakyung Ko, JiHye Kim
 */
@Entity
@Table(name="orders")
@AttributeOverride(name="idx", column=@Column(name="orders_idx"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Orders extends BaseEntity{

    /** Orders Table Fields **/
    @Column(name="number", nullable=false, unique=true)
    int number;

    /** Relation Mapping **/
    /** Orders - AccountOrders 양방향 매핑 **/
    @JsonIgnore
    @OneToMany(mappedBy="orders",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<AccountOrders> accountOrders = new HashSet<>();

    /** Orders - Project 양방향 매핑 **/
    @JsonIgnore
    @OneToMany(mappedBy="orders",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<Project> projects = new HashSet<>();


    /** Method **/
    @Builder
    public Orders(int number, Set<AccountOrders> accountOrders, Set<Project> projects){
        this.number = number;

        this.accountOrders = Optional.ofNullable(accountOrders).orElse(this.accountOrders);
        this.projects = Optional.ofNullable(projects).orElse(this.projects);
    }
}
