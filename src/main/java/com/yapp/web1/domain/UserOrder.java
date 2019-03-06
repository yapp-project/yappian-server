package com.yapp.web1.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * User와 Order(기수)의 연관관계 테이블
 *
 * @author Dakyung Ko, Jihye Kim
 */

@Entity
@Table(name="user_order")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserOrder extends BaseEntity{

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="user_idx",
            foreignKey = @ForeignKey(name="fk_userOrder_user"),
            nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="order_idx",
            foreignKey = @ForeignKey(name="fk_userOrder_order"),
            nullable = false)
    private Order order;

    @Builder
    public UserOrder(User user, Order order){
        this.user = user;
        this.order = order;
    }
}
