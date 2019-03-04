package com.yapp.web1.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * File 테이블의 Domain 클래스
 *
 * @author Dakyung Ko, Jihye Kim
 */

@Entity
@Table(name="file")
@AttributeOverride(name="idx", column=@Column(name="file_idx"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class File extends BaseEntity {
    @Column(name="file_url", nullable = false)
    private String fileURL;

    @Column(name="file_name", nullable = false)
    private String fileName;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="task_idx",
            foreignKey = @ForeignKey(name="fk_file_task"),
            nullable = false)
    private Task task;

    @Builder
    public File(String fileURL, String fileName, Task task){
        this.fileURL = fileURL;
        this.fileName = fileName;
        this.task = task;
    }

}
