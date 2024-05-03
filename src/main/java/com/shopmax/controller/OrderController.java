package com.shopmax.controller;

import com.shopmax.dto.OrderDto;
import com.shopmax.dto.OrderHistDto;
import com.shopmax.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {

    //의존성 주입
    private final OrderService orderService;

    //주문
    @PostMapping(value = "/order")
    public @ResponseBody ResponseEntity order(
            //OrderDto는 ajax에서 전달해 준 데이터를 받아온다.
            //http 통신에서 body에 담겨져서오므로 반드시 @RequestBody 어노테이션을 추가해야한다.
            @RequestBody @Valid OrderDto orderDto, BindingResult bindingResult,
            Principal principal) { //Principal을 이용하면 로그인한 사용자의 정보를 쉽게 가져올 수 있다.

        //유효성 체크에 대한 에러 처리
        if (bindingResult.hasErrors()) {
            StringBuffer sb = new StringBuffer();

            //유효성 체크 후 에러결과를 가져온다.
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            //DTO에 있는 에러 메세지를 포함한 에러 정보들을 bindingResult.getFieldErrors()로 가져올 수 있다.

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage()); //에러메세지를 가지고 온다.
            }

            //ew ResponseEntity<첫번째 매개변수의 타입>(response 데이터, response status 코드);
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
            //sb.toString()엔 에러 메세지를, HttpStatus.BAD_REQUEST로 에러 코드를 리턴한다.
        }

        String email = principal.getName(); //id를 가져온다.(여기선 email)
        Long orderId;

        //주문 실행
        try {
            orderId = orderService.order(orderDto, email); //주문하기
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(orderId, HttpStatus.OK); //성공시
    }

    //주문내역 페이지
    @GetMapping(value = {"/orders", "/orders/{page}"})
    public String orderHist(@PathVariable("page") Optional<Integer> page,
                            Principal principal, Model model) {
                            //Principal은 로그인한 사용자의 정보를 가지고 있다.

        //한 페이지당 4개의 게시물을 보여줌
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);

        Page<OrderHistDto> orderHistDtoPList = orderService.getOrderList(principal.getName(), pageable);

        model.addAttribute("orders", orderHistDtoPList);
        model.addAttribute("maxPage", 5);

        return "order/orderHist";
    }

    //주문 취소(Ajax로 처리)
    @PatchMapping("/order/{orderId}/cancel") //PatchMapping : 일부를 update시 사용
    public @ResponseBody ResponseEntity cancelOrder(@PathVariable("orderId") Long orderId,
                                                    Principal principal) {

        //1. 주문 취소 권한이 있는지 확인(본인 확인)
        if (!orderService.validateOrder(orderId, principal.getName())) {
            return new ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        //2. 주문 취소
        orderService.cancelOrder(orderId);

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

    //주문 삭제(Ajax로 처리)
    @DeleteMapping("/order/{orderId}/delete")
    public @ResponseBody ResponseEntity deleteOrder(@PathVariable("orderId") Long orderId,
                                                    Principal principal) {

        //1. 본인확인
        if (!orderService.validateOrder(orderId, principal.getName())) {
            return new ResponseEntity<String>("주문 삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        //2. 주문 삭제
        orderService.deleteOrder(orderId);

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
}
