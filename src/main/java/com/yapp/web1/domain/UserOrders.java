package com.yapp.web1.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * User와 Orders(기수)의 연관관계 테이블
 *
 * @author Dakyung Ko, Jihye Kim
 */
@Entity
@Table(name="user_orders")
@AttributeOverride(name="idx", column=@Column(name="user_orders_idx"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserOrders extends BaseEntity{

    /** Relation Mapping **/
    /** UserOrders - User 양방향 매핑 **/
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="user_idx",
            foreignKey = @ForeignKey(name="fk_user_orders_user"),
            nullable = false)
    private User user;

    /** UserOrders - Orders 양방향 매핑 **/
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="orders_idx",
            foreignKey = @ForeignKey(name="fk_user_orders_orders"),
            nullable = false)
    private Orders orders;


    /** Method **/
    @Builder
    public UserOrders(User user, Orders order){
        this.user = user;
        this.orders = orders;
    }
}
