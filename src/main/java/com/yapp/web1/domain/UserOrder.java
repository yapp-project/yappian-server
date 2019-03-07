package com.yapp.web1.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * User와 ClubOrder(기수)의 연관관계 테이블
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
            foreignKey = @ForeignKey(name="fk_user_order_user"),
            nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="club_order_idx",
            foreignKey = @ForeignKey(name="fk_user_order_club_order"),
            nullable = false)
    private ClubOrder clubOrder;

    @Builder
    public UserOrder(User user, ClubOrder clubOrder){
        this.user = user;
        this.clubOrder = clubOrder;
    }
}
