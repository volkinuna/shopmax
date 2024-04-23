package com.shopmax.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class}) //audit 기능을 사용하기 위해 작성
@MappedSuperclass //부모 클래스를 상속받는 자식 클래스한테 매핑정보를 제공하기 위해
@Getter
@Setter
public abstract class BaseTimeEntity { //테이블로 생성되는 엔티티 X, 컬럼으로 사용

    @CreatedDate //최초로 게시물을 등록한 날짜를 저장 및 감지
    @Column(updatable = false) //해당 컬럼에 대한 값은 업데이트 금지
    private LocalDateTime regTime; //등록일

    @LastModifiedDate //게시물을 수정한 날짜를 저장 및 감지
    private LocalDateTime updateTime; //수정일
}
