package com.khu.bbangting.domain.order.service;

import com.khu.bbangting.domain.bread.dto.BreadInfoDto;
import com.khu.bbangting.domain.order.dto.OrderFormDto;
import com.khu.bbangting.domain.order.dto.OrderHistDto;
import com.khu.bbangting.domain.review.dto.ReviewFormDto;
import com.khu.bbangting.domain.review.model.Review;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.order.model.Order;
import com.khu.bbangting.domain.order.model.OrderStatus;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.bread.repository.BreadRepository;
import com.khu.bbangting.domain.order.repository.OrderRepository;
import com.khu.bbangting.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    // 주문하기
    public void addOrder(User user, Bread bread, OrderFormDto request) {

        // 이미 예약한 빵팅인지 확인
        if (orderRepository.existsByUserIdAndBreadId(user.getId(), bread.getId())) {
            throw new CustomException(ErrorCode.ORDER_IS_EXIST);}
        //빵 재고 확인
        if (bread.getStock() < request.getQuantity()) {
            throw new CustomException(ErrorCode.BREAD_SOLD_OUT);}
        //주문 저장
        Order savedOrder = orderRepository.save(request.toEntity(user, bread));
        savedOrder.setOrderStatus(OrderStatus.ORDER);
        bread.addOrder(request.toEntity(user, bread));
        bread.removeStock(request.getQuantity());
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

    // 유저 구매내역
    @Transactional(readOnly = true)
    public List<OrderHistDto> getOrderList(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Order> result = orderRepository.findAllByUserId(userId);

        return result.stream().map(orderList -> fromOrder(orderList)).collect(Collectors.toList());
    }

    public OrderHistDto fromOrder(Order order) {

        OrderHistDto orderHistDto = OrderHistDto.builder()
                .orderId(order.getId())
                .breadInfoDto(BreadInfoDto.fromBread(order.getBread()))
                .quantity(order.getQuantity())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .build();

        return orderHistDto;
    }

}
