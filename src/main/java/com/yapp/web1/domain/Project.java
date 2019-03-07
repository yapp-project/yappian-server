package com.yapp.web1.domain;

import com.yapp.web1.domain.VO.Mark;
import com.yapp.web1.domain.VO.ProjectType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Project 테이블의 Domain 클래스
 *
 * @author Dakyung Ko, Jihye Kim
 */

@Entity
@Table(name="project")
@AttributeOverride(name="idx", column=@Column(name="project_idx"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Project extends BaseEntity {

    @Column(name="type", nullable = false)
    private ProjectType type;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="final_check", nullable = false)
    private Mark finalCheck = Mark.N; //

    @Column(name="description")
    private String description; //

    @Column(name="url")
    private String productURL; //

    @Column(name="create_user_idx",nullable = false)
    private Long createUserIdx;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="orders_idx",
            foreignKey = @ForeignKey(name="fk_project_orders"),
            nullable = false)
    private Orders orders;

    // @OneToOne file_idx 완료시 이미지 여부 (에디터 사용에 따라 달라짐)

    @Builder
    public Project(ProjectType type, String name, Long createUserIdx, Orders orders){
        this.type = type;
        this.name = name;
        this.createUserIdx = createUserIdx;
        this.orders = orders;
    }

    public Mark finishedProject(){
        this.finalCheck = Mark.Y;
        return this.finalCheck;
    }

    public void descriptProject(String description){
        this.description = description;
    }

    public void updateProductURL(String productURL){
        this.productURL = productURL;
    }
}
