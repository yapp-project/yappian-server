package com.yapp.web1.domain;

import com.yapp.web1.converter.FileTypeAttributeConverter;
import com.yapp.web1.converter.MarkAttributeConverter;
import com.yapp.web1.domain.VO.FileType;
import com.yapp.web1.domain.VO.Mark;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
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
    /** File Table Fields **/

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="file_url", nullable = false)
    private String fileURL;

    @Column(name="type", nullable = false)
    @Convert(converter = FileTypeAttributeConverter.class)
    private FileType type;

    /** Relation Mapping **/
    /** File - Project 단방향 매핑 **/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="project_idx",
            foreignKey = @ForeignKey(name="fk_file_project"),
            nullable = false)
    private Project project;

    /** Method **/
    @Builder
    public File(String name, String fileURL, FileType type,  Project project){
        this.name = name;
        this.fileURL = fileURL;
        this.type = type;
        this.project = project;
    }

}
