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
@Table(name = "project")
@AttributeOverride(name = "idx", column = @Column(name = "project_idx"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
//@EqualsAndHashCode(of = {"idx", "name"}) // 잘 적용되나 확인해봐야 함
public class Project extends BaseEntity {
    /**
     * Project Table Fields
     **/
    @Column(name = "type", nullable = false)
    @Convert(converter = ProjectTypeAttributeConverter.class)
    private ProjectType type;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "final_check", nullable = false)
    @Convert(converter = MarkAttributeConverter.class)
    private Mark finalCheck = Mark.N;

    @Column(name = "release_check", nullable = false)
    @Convert(converter = MarkAttributeConverter.class)
    private Mark releaseCheck = Mark.N;

    @Column(name = "description")
    private String description;

    @Column(name = "url")
    private String productURL;

    @Column(name = "create_user_idx", nullable = false)
    private Long createUserIdx;

    /** Relation Mapping **/
    /**
     * Project - Orders 양방향 매핑
     **/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orders_idx",
            foreignKey = @ForeignKey(name = "fk_project_orders"), nullable = false)
    private Orders orders;

    /**
     * Project - Url 양방향 매핑
     **/
    @JsonIgnore
    @OneToMany(mappedBy = "project",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<Url> urlList = new ArrayList<>();

    /** Relation Mapping - Join Table **/
    /**
     * Project - User 양방향 매핑
     **/
    @ManyToMany(mappedBy = "joinedProjects",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    private Set<User> userList = new HashSet<>();

    /**
     * Project - File 단방향 매핑
     **/
    @JsonIgnore
    @OneToMany(mappedBy = "project",
            fetch = FetchType.LAZY)
    private List<File> fileList = new ArrayList<>();

    /**
     * Method
     **/
    @Builder
    public Project(ProjectType type, String name, String password, Mark finalCheck, Mark releaseCheck, String description,
                   String productURL, Long createUserIdx, Orders orders, List<Url> urlList, Set<User> userList) {
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

    // setter - name
    public void setName(String name) {
        this.name = name;
    }

    // setter - type
    public void setType(ProjectType type) {
        this.type = type;
    }

    // setter - pasword
    public void setPassword(String password) {
        this.password = password;
    }

    // setter - order
    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    // setter - userList
    public void setUserList(Set<User> userList) {
        this.userList = userList;
    }

    // setter - finalCheck
    public void setFinalCheck(Mark finalCheck) {
        this.finalCheck = finalCheck;
    }

    // setter - description
    public void setDescription(String description) {
        this.description = description;
    }

    // setter - projectUrl
    public void setProductURL(String productURL) {
        this.productURL = productURL;
    }

    // setter - fileList
    public void setFileList(List<File> fileList){this.fileList = fileList;}
}
