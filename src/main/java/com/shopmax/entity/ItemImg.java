package com.shopmax.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "item_img")
@Getter
@Setter
@ToString
public class ItemImg extends BaseEntity {

    @Id
    @Column(name = "item_img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgName; //UUID로 바뀐 이미지 파일명

    private String oriImgName; //원본 이미지 파일명

    private String imgUrl; //이미지 경로

    private String repImgYn; // 대표 이미지 여부(Y : 썸네일 이미지, N : 일반 이미지)

    @ManyToOne(fetch = FetchType.LAZY) //LAZY : 지연 로딩, 기본은 즉시 로딩
    @JoinColumn(name = "item_id")
    private Item item; //ItemImg가 Item을 참조한다.
}
