package com.shopmax.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "order_item")
@Getter
@Setter
@ToString
public class OrderItem {

    @Id
    @Column(name = "order_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int orderPrice; //주문가격

    private int count; //주문수량

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item; //OrderItem이 Item을 참조한다.

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order; //OrderItem이 Order를 참조한다.
}
