package com.khu.bbangting.service;

import com.khu.bbangting.dto.order.OrderFormDto;
import com.khu.bbangting.dto.order.OrderHistDto;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import com.khu.bbangting.model.Bread;
import com.khu.bbangting.model.Order;
import com.khu.bbangting.model.OrderStatus;
import com.khu.bbangting.model.User;
import com.khu.bbangting.repository.BreadRepository;
import com.khu.bbangting.repository.OrderRepository;
import com.khu.bbangting.repository.UserRepository;
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

    /**
     * 구매하기
     */
    public Long addOrder(OrderFormDto requestDto) {

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Bread bread = breadRepository.findById(requestDto.getBreadId())
                .orElseThrow(() -> new CustomException(ErrorCode.BREAD_SOLD_OUT));

        //상품 재고 확인
        if (bread.isSoldOut()) {
            throw new CustomException(ErrorCode.BREAD_SOLD_OUT);
        }
        //빵팅 시간 확인
        if (!bread.isTingTime()) {
            throw new CustomException(ErrorCode.NOT_BBANGTING_TIME);
        }
        //주문 등록
        Order savedOrder = orderRepository.save(requestDto.toEntity(user, bread));

        return savedOrder.getId();
    }

    /**
     * 구매 취소
     */
    public void cancelOrder(Long orderId) {
        orderRepository.findById(orderId)
                .ifPresentOrElse(order -> {
                    //재고량 다시 추가
                    order.getBread().addStock(order.getQuantity());
                    //주문 삭제
                    order.setOrderStatus(OrderStatus.CANCEL);
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