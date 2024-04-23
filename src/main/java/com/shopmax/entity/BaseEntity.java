package com.shopmax.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(value = {AuditingEntityListener.class}) //audit 기능을 사용하기 위해 작성
@MappedSuperclass //다른 엔티티에서 부모 클래스로 사용하기 위해
@Getter
@Setter
public abstract class BaseEntity extends BaseTimeEntity { //테이블로 생성되는 엔티티 X, 컬럼으로 사용

    @CreatedBy //최초로 게시물을 등록한 사람의 id를 저장 및 감지
    @Column(updatable = false) //해당 컬럼에 대한 값은 업데이트 금지
    private String createdBy; //등록자

    @LastModifiedBy //게시물을 수정한 사람의 id를 저장 및 감지
    private String modifiedBy; //수정자
}