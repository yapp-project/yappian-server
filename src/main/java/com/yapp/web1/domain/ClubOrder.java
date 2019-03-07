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

/**
 * ClubOrder 테이블(프로젝트 기수)의 Domain 클래스
 *
 * @author Dakyung Ko
 */

@Entity
@Table(name="club_order")
@AttributeOverride(name="idx", column=@Column(name="club_order_idx"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ClubOrder extends BaseEntity{

    @Column(name="num", nullable=false, unique=true)
    Long num;

    @JsonIgnore
    @OneToMany(mappedBy="clubOrder",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<UserOrder> userOrders = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy="clubOrder",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<Project> projects = new HashSet<>();

    @Builder
    public ClubOrder(Long num, Set<UserOrder> userOrders, Set<Project> projects){
        this.num = num;

        this.userOrders = Optional.ofNullable(userOrders).orElse(this.userOrders);
        this.projects = Optional.ofNullable(projects).orElse(this.projects);
    }
}
