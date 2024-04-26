package com.shopmax.entity;

import com.shopmax.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") //DB에서 order by 예약어를 사용하므로 orders라고 지정
@Getter
@Setter
@ToString
public class Order { //클래스명은 설계도이므로 복수형으로 쓰지 않는다.

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate; //주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문상태

    @ManyToOne(fetch = FetchType.LAZY) //LAZY : 지연 로딩, 기본은 즉시 로딩
    @JoinColumn(name = "member_id")
    private Member member; //Order가 Member를 참조한다.

    //mappedBy : 연관관계의 주인을 설정(order는 주인이 아니다.)
    //cascade : 영속성 전이(엔티티의 상태를 변경할때 해당 엔티티와 연관된 엔티티의 상태 변화를 전파)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true) // orphanRemoval = true : 고아 객체 만들기. 부모 엔티티와 연관관계를 끊어 자식 엔티티에 저장된 레코드 값만 제거하고 싶을때 사용
    private List<OrderItem> orderItems = new ArrayList<>(); //주문할때 여러 아이템을 주문할 수 있으니 List로 받아야한다.

    //★양방향 참조시 save를 진행할때 서로가 참조하는 객체를 꼭 넣어줘야한다.
    //OrderItem은 Order를 참조한다.
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this); //★양방향 참조 관계일때는 OrderItem 객체에도 Order 객체를 세팅
    }

    //★양방향 참조시 save를 진행할때 서로가 참조하는 객체를 꼭 넣어줘야한다.
    //Order도 OrderItem을 참조한다.
    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setMember(member);

        for (OrderItem orderItem : orderItemList) {
            order.addOrderItem(orderItem);
        }

        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    //총 주문 금액 구하는 메소드
    public int getTotalPrice() {
        int totalPrice = 0;

        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }

        return totalPrice;
    }
}
