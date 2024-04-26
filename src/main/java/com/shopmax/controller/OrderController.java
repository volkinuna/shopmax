package com.shopmax.controller;

import com.shopmax.dto.OrderDto;
import com.shopmax.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    //의존성 주입
    private final OrderService orderService;

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
}
