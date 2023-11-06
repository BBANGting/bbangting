package com.khu.bbangting.controller;

import com.khu.bbangting.dto.OrderDto;
import com.khu.bbangting.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.Binding;
import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {
    private OrderService orderService;

    //주문
    @PostMapping("/order")
    public @ResponseBody ResponseEntity createOrder(@RequestBody @Valid OrderDto orderDto, BindingResult bindingResult) {

        // 주문 정보를 받는 orderDto 객체에 데이터 바인딩 시 에러가 있는지 검사
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }

            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        System.out.println("userId: " + ", breadId: " + orderDto.getBreadId() + ", quantity: " + orderDto.getQuantity());
        return new ResponseEntity<>(orderService.createOrder(orderDto.getUserId(), orderDto.getBreadId(), orderDto.getQuantity()), HttpStatus.OK);
    }

}
