package com.yapp.web1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yapp.web1.converter.MarkAttributeConverter;
import com.yapp.web1.converter.ProjectTypeAttributeConverter;
import com.yapp.web1.domain.VO.Mark;
import com.yapp.web1.domain.VO.ProjectType;
import lombok.*;

import javax.persistence.*;
import java.util.*;

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
//@EqualsAndHashCode(of = {"idx", "name"}) // 잘 적용되나 확인해봐야 함
public class Project extends BaseEntity {
    /** Project Table Fields **/
    @Column(name="type", nullable = false)
    @Convert(converter = ProjectTypeAttributeConverter.class)
    private ProjectType type;

    @Column(name="password",nullable = false)
    private String password;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name="final_check", nullable = false)
    @Convert(converter = MarkAttributeConverter.class)
    private Mark finalCheck = Mark.N;

    @Column(name="release_check")
    @Convert(converter = MarkAttributeConverter.class)
    private Mark releaseCheck = Mark.N;

    @Column(name="description")
    private String description;

    @Column(name="url")
    private String productURL;

    @Column(name="create_user_idx",nullable = false)
    private Long createUserIdx;

    /** Relation Mapping **/
    /** Project - Orders 양방향 매핑 **/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="orders_idx",
            foreignKey = @ForeignKey(name="fk_project_orders"),
            nullable = false)
    private Orders orders;

    /** Project - Url 양방향 매핑 **/
    @JsonIgnore
    @OneToMany(mappedBy = "project",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<Url> urlList = new ArrayList<>();

    /** Relation Mapping - Join Table **/
    /** Project - User 양방향 매핑 **/
    @ManyToMany(mappedBy = "joinedProjects",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    private Set<User> userList = new HashSet<>();

    /** Project - File 단방향 매핑 **/
    @JsonIgnore
    @OneToMany(mappedBy = "url",
            fetch = FetchType.LAZY)
    private List<File> fileList = new ArrayList<>();

    /** Method **/
    @Builder
    public Project(ProjectType type, String name, String password, Mark finalCheck, Mark releaseCheck, String description,
                   String productURL, Long createUserIdx, Orders orders, List<Url> urlList, Set<User> userList){
        this.type = type;
        this.name = name;
        this.password = password;
        this.finalCheck = finalCheck;
        this.releaseCheck = releaseCheck;
        this.description = description;
        this.productURL = productURL;
        this.createUserIdx = createUserIdx;
        this.orders = orders;

        this.urlList = Optional.ofNullable(urlList).orElse(this.urlList);
        this.userList = Optional.ofNullable(userList).orElse(this.userList);
    }

    public Mark finishedProject(){
        this.finalCheck = Mark.Y;
        return this.finalCheck;
    }

    public void describeProject(String description){
        this.description = description;
    }

    public void updateProductURL(String productURL){
        this.productURL = productURL;
    }

    //기수, 타입, 이름
    public void updateProject(String name, ProjectType type, Orders orders){
        this.name = name;
        this.type = type;
        this.orders = orders;
    }
}
