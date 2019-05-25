package com.yapp.web1.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Account와 Orders(기수)의 연관관계 테이블
 *
 * @author Dakyung Ko, Jihye Kim
 */
@Entity
@Table(name="account_orders")
@AttributeOverride(name="idx", column=@Column(name="account_orders_idx"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AccountOrders extends BaseEntity{

    /** Relation Mapping **/
    /** AccountOrders - Account 양방향 매핑 **/
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="account_idx",
            foreignKey = @ForeignKey(name="fk_account_orders_account"),
            nullable = false)
    private Account account;

    /** AccountOrders - Orders 양방향 매핑 **/
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="orders_idx",
            foreignKey = @ForeignKey(name="fk_account_orders_orders"),
            nullable = false)
    private Orders orders;


    /** Method **/
    @Builder
    public AccountOrders(Account account, Orders order){
        this.account = account;
        this.orders = orders;
    }
}
