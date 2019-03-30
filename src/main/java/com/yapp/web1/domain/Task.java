package com.yapp.web1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yapp.web1.converter.TaskJobAttributeConverter;
import com.yapp.web1.converter.TaskStatusAttributeConverter;
import com.yapp.web1.domain.VO.TaskJob;
import com.yapp.web1.domain.VO.TaskStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Task 테이블의 Domain 클래스
 *
 * @author Dakyung Ko, Jihye Kim
 */

@Entity
@Table(name="task")
@AttributeOverride(name="idx", column=@Column(name="task_idx"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Task extends BaseEntity {

    /** Task Table Fields **/
    @Column(name="title", nullable = false)
    private String title;

    @Column(name="status", nullable = false)
    @Convert(converter = TaskStatusAttributeConverter.class)
    private TaskStatus status = TaskStatus.DOING;

    @Column(name="job", nullable = false)
    @Convert(converter = TaskJobAttributeConverter.class)
    private TaskJob job;

    @Column(name="start_date", nullable = false)
    private LocalDate startDate;

    @Column(name="end_date", nullable = false)
    private LocalDate endDate;

    @Column(name="contents")
    private String contents;

    /** Relation Mapping **/
    /** Task - Project 양방향 매핑 **/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_idx",
            foreignKey = @ForeignKey(name="fk_task_project"),
            nullable = false)
    private Project project;

    /** Task - Read 양방향 매핑 **/
    @JsonIgnore
    @OneToMany(mappedBy = "task",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, // LAZY
            orphanRemoval = true)
    private List<Read> readList = new ArrayList<>();

    /** Task - Comment 양방향 매핑 **/
    @JsonIgnore
    @OneToMany(mappedBy = "task",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    /** Task - File 양방향 매핑 **/
    @JsonIgnore
    @OneToMany(mappedBy = "task",
            fetch = FetchType.LAZY)
    private List<File> fileList = new ArrayList<>();

    /** Relation Mapping - Join Table **/
    /** Task - User 단방향 매핑 **/
    @OneToMany(fetch=FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinTable(name="work",
            joinColumns = @JoinColumn(name="task_idx"),
            inverseJoinColumns = @JoinColumn(name="user_idx"))
    private List<User> works = new ArrayList<>();

    /** Method **/
    @Builder
    public Task(String title, TaskStatus status, TaskJob job, LocalDate startDate, LocalDate endDate, String contents,
                Project project, List<Read> readList, List<Comment> commentList, List<File> fileList, List<User> works){
        this.title = title;
        this.status = status;
        this.job = job;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contents = contents;
        this.project = project;

        this.readList = Optional.ofNullable(readList).orElse(this.readList);
        this.commentList = Optional.ofNullable(commentList).orElse(this.commentList);
        this.fileList = Optional.ofNullable(fileList).orElse(this.fileList);
        this.works = Optional.ofNullable(works).orElse(this.works);
    }
}
