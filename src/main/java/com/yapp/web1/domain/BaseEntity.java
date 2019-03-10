package com.yapp.web1.domain;

import lombok.Getter;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.GenerationType;
import javax.persistence.Column;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Entity 클래스의 공통 분모를 모아둔 부모 클래스
 * (idx, 생성일, 수정일)
 *
 * @author Dakyung Ko, JiHye Kim
 */

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long idx;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    protected LocalDateTime createDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    protected LocalDateTime modifiedDate;
}
