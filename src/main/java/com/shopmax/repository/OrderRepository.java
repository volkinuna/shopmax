package com.shopmax.repository;

import com.shopmax.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    //현재 로그인한 사용자의 주문 데이터를 페이징 조건에 맞춰서 조회
    @Query("select o from Order o where o.member.email = :email order by o.orderDate desc") //JPQL은 엔티티에서 가져온다.
    List<Order> findOrders(@Param("email") String email, Pageable pageable);
    //where o.member -> 로그인한 사용자의 주문내역만 가져와야하므로 / order by o.orderDate desc -> 주문내역은 최근것이 위로 쌓이므로 desc로..

    //현재 로그인한 회원의 주문 갯수가 몇개인지 조회(total)
    @Query("select count(o) from Order o where o.member.email = :email")
    Long countOrder(@Param("email") String email);
}

