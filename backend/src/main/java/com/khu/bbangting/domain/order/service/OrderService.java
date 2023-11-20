package com.khu.bbangting.domain.order.service;

import com.khu.bbangting.domain.order.dto.OrderFormDto;
import com.khu.bbangting.domain.order.dto.OrderHistDto;
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


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BreadRepository breadRepository;

    // 주문하기
    public Long addOrder(OrderFormDto requestDto) {

        // 엔티티 조회 (유저 & 빵팅)
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Bread bread = breadRepository.findById(requestDto.getBreadId())
                .orElseThrow(() -> new CustomException(ErrorCode.BREAD_SOLD_OUT));

        //빵 재고 확인
        if (bread.isSoldOut()) {
            throw new CustomException(ErrorCode.BREAD_SOLD_OUT);
        }
        //빵팅 시간 확인
        if (!bread.isTingTime()) {
            throw new CustomException(ErrorCode.NOT_BBANGTING_TIME);
        }
        //주문 저장
        Order savedOrder = orderRepository.save(requestDto.toEntity(user, bread));

        return savedOrder.getId();
    }

    // 주문 취소하기
    public void cancelOrder(Long orderId) {
        // 엔티티 조회 (주문)
        orderRepository.findById(orderId)
                .ifPresentOrElse(order -> {
                    // 재고 추가
                    order.getBread().addStock(order.getQuantity());
                    // 주문상태 취소로 전환
                    order.setOrderStatus(OrderStatus.CANCEL);
                    // 주문 취소
                    orderRepository.delete(order);
                }, () -> {
                    throw new CustomException(ErrorCode.ORDER_NOT_FOUND);
                });
    }

    /**
     * 유저 구매내역
     **/
    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        //전체 주문내역에서 유저 주문만 리스트로
        List<Order> orderList = orderRepository.findOrders(email, pageable);

        //유저 주문리스트에서 주문내역 정보 가져오기
        List<OrderHistDto> orderHistDtoList = new ArrayList<>();
        for(Order order : orderList) {
            OrderHistDto orderHistDto = new OrderHistDto(order);
            orderHistDto.addOrderHistDto(orderHistDto);
            orderHistDtoList.add(orderHistDto);
        }

        return new PageImpl<OrderHistDto>(orderHistDtoList);
    }

}