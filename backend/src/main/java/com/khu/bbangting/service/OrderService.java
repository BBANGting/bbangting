package com.khu.bbangting.service;

import com.khu.bbangting.dto.OrderDto;
import com.khu.bbangting.dto.OrderHistDto;
import com.khu.bbangting.model.Bread;
import com.khu.bbangting.model.Order;
import com.khu.bbangting.model.User;
import com.khu.bbangting.repository.OrderRepository;
import com.khu.bbangting.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private BreadRepository breadRepository;
    private UserRepository userRepository;
    private OrderRepository orderRepository;

    //주문
    public Long order(OrderDto orderDto, String email) {

        Bread bread = breadRepository.findById(orderDto.getBreadId())
                .orElseThrow(EntityNotFoundException::new);

        User user = userRepository.findByEmail(email);

        Order order = Order.createOrder(user, bread, orderDto.getQuantity());
        orderRepository.save(order);

        return order.getId();
    }

    //주문 취소
    public Long cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        order.cancel();

        return orderId;
    }
}
