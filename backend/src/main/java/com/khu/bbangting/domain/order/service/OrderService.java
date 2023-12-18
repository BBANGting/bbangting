package com.khu.bbangting.domain.order.service;

import com.khu.bbangting.domain.order.dto.OrderFormDto;
import com.khu.bbangting.domain.order.dto.OrderHistDto;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.order.model.Order;
import com.khu.bbangting.domain.order.model.OrderStatus;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    // 주문하기
    public Long addOrder(User user, Bread bread, OrderFormDto requestDto) {

        // 이미 예약한 빵팅인지 확인
        if (orderRepository.existsByBreadAndUser(bread, user)) {
            throw new CustomException(ErrorCode.ORDER_IS_EXIST);}
        //빵 재고 확인
        if (bread.getStock() < requestDto.getQuantity()) {
            throw new CustomException(ErrorCode.BREAD_SOLD_OUT);}
        //주문 저장
        Order savedOrder = orderRepository.save(requestDto.toEntity(user, bread));
        bread.addOrder(requestDto.toEntity(user, bread));
        bread.removeStock(requestDto.getQuantity());

        return savedOrder.getId();
    }

    // 주문 취소하기
    public void cancelOrder(Long orderId) {
        // 엔티티 조회 (주문)
        orderRepository.findById(orderId)
                .ifPresentOrElse(order -> {
                    // 재고 추가
                    order.getBread().addStock(order.getQuantity());
                    // Bread Entity의 orders 리스트에서 삭제
                    order.getBread().removeOrder(order);
                    // 주문상태 취소로 전환
                    order.setOrderStatus(OrderStatus.CANCEL);
                }, () -> {
                    throw new CustomException(ErrorCode.ORDER_NOT_FOUND);
                });
    }

    // 유저 주문내역
    @Transactional(readOnly = true)
    public List<OrderHistDto> getOrderList(Long userId) {

        List<Order> orderList = orderRepository.findAllByUserIdAndOrderStatus(userId, OrderStatus.ORDER);

        List<OrderHistDto> orderHistList = new ArrayList<>();
        for (Order order : orderList) {
            OrderHistDto orderHistDto = OrderHistDto.builder()
                    .breadName(order.getBread().getBreadName())
                    .price(order.getBread().getPrice() * order.getQuantity())
                    .quantity(order.getQuantity())
                    .orderDate(order.getOrderDate()).build();

            orderHistList.add(orderHistDto);

        }
        return orderHistList;
    }

}