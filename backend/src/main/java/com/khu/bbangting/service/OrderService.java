package com.khu.bbangting.service;

import com.khu.bbangting.dto.OrderDto;
import com.khu.bbangting.dto.OrderHistDto;
import com.khu.bbangting.exception.BreadNotFoundException;
import com.khu.bbangting.exception.UserNotFoundException;
import com.khu.bbangting.model.Bread;
import com.khu.bbangting.model.Order;
import com.khu.bbangting.model.User;
import com.khu.bbangting.repository.BreadRepository;
import com.khu.bbangting.repository.OrderRepository;
import com.khu.bbangting.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private BreadRepository breadRepository;
    private UserRepository userRepository;
    private OrderRepository orderRepository;

    //주문
    public Long createOrder(Long userId, Long breadId, int quantity) {

        //회원 및 상품 찾기
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("존재하지 않는 사용자입니다."));
        Bread bread = breadRepository.findById(breadId).orElseThrow(
                () -> new BreadNotFoundException("존재하지 않는 상품 입니다"));

        //주문 생성
        Order order = Order.builder()
                .quantity(quantity)
                .build();

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    //주문 목록

    //주문 취소
    public Long cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        order.cancel();

        return orderId;
    }
}
