package com.shopmax.dto;

import com.shopmax.constant.OrderStatus;
import com.shopmax.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderHistDto {

    //엔티티 -> DTO로 변환
    //orderDate에 fomat을 써야하므로 modelMapper를 사용하지 않는다.
    public OrderHistDto(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
    }

    private Long orderId; //주문 아이디
    private String orderDate; //주문 날짜
    private OrderStatus orderStatus; //주문 상태
    private List<OrderItemDto> orderItemDtoList = new ArrayList<>(); //주문 상품 리스트

    //위 orderItemDtoList에 객체를 추가하는 메소드
    public void addOrderItemDto(OrderItemDto orderItemDto) {
        this.orderItemDtoList.add(orderItemDto);
    }
}
