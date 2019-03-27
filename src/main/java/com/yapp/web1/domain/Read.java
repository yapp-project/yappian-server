package com.yapp.web1.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Read 테이블의 Domain 클래스
 * Task 읽음 여부를 기록
 *
 * @author Dakyung Ko
 */
@Entity
@Table(name="read")
@AttributeOverride(name="idx", column=@Column(name="read_idx"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Read extends BaseEntity{
    /** Relation Mapping **/
    /** Read - Task 양방향 매핑 **/
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="task_idx",
            foreignKey = @ForeignKey(name="fk_read_task"),
            nullable = false)
    private Task task;

    /** Read - User 양방향 매핑 **/
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="user_idx",
            foreignKey = @ForeignKey(name="fk_read_user"),
            nullable = false)
    private User user;

    /** Method **/
    @Builder
    public Read(Task task, User user){
        this.task = task;
        this.user = user;
    }
}
