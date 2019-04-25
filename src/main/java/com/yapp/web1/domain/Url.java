package com.yapp.web1.domain;

import com.yapp.web1.converter.UrlTypeAttributeConverter;
import com.yapp.web1.domain.VO.UrlType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Url 테이블의 Domain 클래스
 *
 * @author Dakyung Ko, Jihye Kim
 */

@Entity
@Table(name="url")
@AttributeOverride(name="idx", column=@Column(name="url_idx"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Url extends BaseEntity {

    /** Url Table Fields **/
    @Column(name="title")
    private String title;

    @Column(name="contents")
    private String contents;

    @Column(name="type")
    @Convert(converter = UrlTypeAttributeConverter.class)
    private UrlType type;

    /** Relation Mapping **/
    /** Url - Project 양방향 매핑 **/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_idx",
            foreignKey = @ForeignKey(name="fk_task_project"),
            nullable = false)
    private Project project;

    /** Method **/
    @Builder
    public Url(String title, UrlType type, String contents, Project project){
        this.title = title;
        this.type = type;
        this.contents = contents;
        this.project = project;
    }
}
