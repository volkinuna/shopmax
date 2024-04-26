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
public class OrderItem extends BaseEntity {

    @Id
    @Column(name = "order_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int orderPrice; //주문가격

    private int count; //주문수량

    @ManyToOne(fetch = FetchType.LAZY) //LAZY : 지연 로딩, 기본은 즉시 로딩
    @JoinColumn(name = "item_id")
    private Item item; //OrderItem이 Item을 참조한다.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; //OrderItem이 Order를 참조한다.

    //주문 상품 정보 - 재고 감소를 위해 createOrderItem를 만듬(OrderItem을 save할 때 사용)
    public static OrderItem createOrderItem(Item item, int count) {
        OrderItem orderItem = new OrderItem(); //OrderItem 객체를 만들고,
        orderItem.setItem(item); //OrderItem이 Item을 참조하고 있기때문에 OrderItem 객체에 Item을 넣어줘야한다.
        orderItem.setCount(count); //주문 수량
        orderItem.setOrderPrice(item.getPrice());

        item.removeStock(count); //Item 객체 안의 재고 변경

        return orderItem;
    }

    //주문 총 금액 구하기
    public int getTotalPrice() {
        return orderPrice * count; //총 가격
    }
}
