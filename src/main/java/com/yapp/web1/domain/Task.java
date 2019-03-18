package com.yapp.web1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yapp.web1.converter.MarkAttributeConverter;
import com.yapp.web1.converter.TaskJobAttributeConverter;
import com.yapp.web1.converter.TaskStatusAttributeConverter;
import com.yapp.web1.domain.VO.Mark;
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

    @Column(name="read_check", nullable = false)
    @Convert(converter = MarkAttributeConverter.class)
    private Mark readCheck = Mark.N;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_idx",
            foreignKey = @ForeignKey(name="pk_task_project"),
            nullable = false)
    private Project project;

    @JsonIgnore
    @OneToMany(mappedBy = "task",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "task",
            fetch = FetchType.LAZY)
    private List<File> fileList = new ArrayList<>();

    @OneToMany(fetch=FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinTable(name="managers",
            joinColumns = @JoinColumn(name="task_idx"),
            inverseJoinColumns = @JoinColumn(name="user_idx"))
    private Set<User> managers = new HashSet<>();

    @Builder
    public Task(String title, TaskStatus status, TaskJob job, LocalDate startDate, LocalDate endDate, String contents,
                Project project, List<Comment> commentList, List<File> fileList, Set<User> managers){
        this.title = title;
        this.status = status; //
        this.job = job;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contents = contents;
        this.project = project;

        this.commentList = Optional.ofNullable(commentList).orElse(this.commentList);
        this.fileList = Optional.ofNullable(fileList).orElse(this.fileList);
        this.managers = Optional.ofNullable(managers).orElse(this.managers);
    }

    public void taskReadCheck(){
        this.readCheck = Mark.Y;
    }
}
