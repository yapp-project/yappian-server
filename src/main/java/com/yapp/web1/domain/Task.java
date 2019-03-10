package com.yapp.web1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yapp.web1.domain.VO.Mark;
import com.yapp.web1.domain.VO.TaskJob;
import com.yapp.web1.domain.VO.TaskStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.swing.text.html.Option;
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
    private TaskStatus status = TaskStatus.DOING;

    @Column(name="job", nullable = false)
    private TaskJob job;

    @Column(name="start_date", nullable = false)
    private LocalDate startDate;

    @Column(name="end_date", nullable = false)
    private LocalDate endDate;

    @Column(name="contents")
    private String contents;

    @Column(name="read_check", nullable = false)
    private Mark readCheck = Mark.N;

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


    //Join Table
    @OneToMany(fetch=FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinTable(name="managers",
            joinColumns = @JoinColumn(name="task_idx"),
            inverseJoinColumns = @JoinColumn(name="user_idx"))
    private Set<User> managers = new HashSet<>();

    @Builder
    public Task(String title, TaskStatus status, TaskJob job, LocalDate startDate, LocalDate endDate, String contents,
                List<Comment> commentList, List<File> fileList, Set<User> managers){
        this.title = title;
        this.status = status; //
        this.job = job;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contents = contents;

        this.commentList = Optional.ofNullable(commentList).orElse(this.commentList);
        this.fileList = Optional.ofNullable(fileList).orElse(this.fileList);
        this.managers = Optional.ofNullable(managers).orElse(this.managers);
    }

    public void taskReadCheck(){
        this.readCheck = Mark.Y;
    }
}
