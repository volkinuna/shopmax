package com.shopmax.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString
public class Cart {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Cart가 Member를 참조한다. (Member : 부모, Cart : 자식)
    @OneToOne
    @JoinColumn(name = "member_id") //Member 테이블에 있는 PK를 FK로 사용하겠다.
    private Member member; 
}
