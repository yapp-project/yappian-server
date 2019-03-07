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

    @Column(name="number", nullable=false, unique=true)
    Long number;

    @JsonIgnore
    @OneToMany(mappedBy="orders",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<UserOrder> userOrders = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy="orders",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<Project> projects = new HashSet<>();

    /*
    @Builder
    public Orders(Long number, Set<UserOrder> userOrders, Set<Project> projects){
        this.number = number;

        this.userOrders = Optional.ofNullable(userOrders).orElse(this.userOrders);
        this.projects = Optional.ofNullable(projects).orElse(this.projects);
    }
    */
}
