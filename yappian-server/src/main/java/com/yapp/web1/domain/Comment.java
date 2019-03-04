package com.yapp.web1.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * Comment 테이블의 Domain 클래스
 *
 * @author Dakyung Ko, Jihye Kim
 */

@Entity
@Table(name="comment")
@AttributeOverride(name="idx", column=@Column(name="comment_idx"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseEntity {

    @Column(name="contents", nullable = false)
    private String contents;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="task_idx",
            foreignKey = @ForeignKey(name="fk_comment_task"),
            nullable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="user_idx",
            foreignKey = @ForeignKey(name="fk_comment_user"),
            nullable = false)
    private User user;

    @Builder
    public Comment(String contents, Task task, User user){
        this.contents = contents;
        this.task = task;
        this.user = user;
    }
}
