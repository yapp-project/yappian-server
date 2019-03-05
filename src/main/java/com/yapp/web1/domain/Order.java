package com.yapp.web1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/*
 * Order 테이블(프로젝트 기수)의 Domain 클래스
 *
 * @author Dakyung Ko
 */

@Entity
@Table(name="order")
@AttributeOverride(name="idx", column=@Column(name="order_idx"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order extends BaseEntity{

    @Column(name="num", nullable=false, unique=true)
    Long num;

    @JsonIgnore
    @OneToMany(mappedBy="order",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<UserOrder> userOrders = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy="order",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<Project> projects = new HashSet<>();

    @Builder
    public Order(Long num, Set<UserOrder> userOrders, Set<Project> projects){
        this.num = num;

        this.userOrders = Optional.ofNullable(userOrders).orElse(this.userOrders);
        this.projects = Optional.ofNullable(projects).orElse(this.projects);
    }
}