package com.khu.bbangting.order;

import com.khu.bbangting.domain.bread.dto.BreadFormDto;
import com.khu.bbangting.domain.order.dto.OrderFormDto;
import com.khu.bbangting.domain.bread.repository.BreadRepository;
import com.khu.bbangting.domain.order.repository.OrderRepository;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.order.model.Order;
import com.khu.bbangting.domain.order.model.OrderStatus;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.order.service.OrderService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired BreadRepository breadRepository;
    @Autowired UserRepository userRepository;
    @Autowired OrderRepository orderRepository;
    @Autowired OrderService orderService;
    @Autowired EntityManager em;

    @Test
    public void addOrder() throws Exception {
        //given
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setNickname("test");
        user.setUsername("test");
        user.setPassword("password12!");
        em.persist(user);

        int stockQuantity = 100;
        Bread bread = new Bread();
        bread.setBreadName("cake");
        bread.setDescription("blabla");
        bread.setPrice(2000);
        bread.setTingTime(LocalDateTime.now());
        bread.setMaxTingNum(stockQuantity);
        bread.setStock(stockQuantity);
        bread.setTingStatus('Y');
        em.persist(bread);

        int orderQuantity = 3;
        OrderFormDto orderFormDto = new OrderFormDto();
        orderFormDto.setUserId(user.getId());
        orderFormDto.setBreadId(bread.getId());
        orderFormDto.setQuantity(orderQuantity);
        orderFormDto.setOrderDate(LocalDateTime.now());
        orderFormDto.setOrderStatus(OrderStatus.ORDER);
        orderFormDto.setBreadFormDto(BreadFormDto.fromBread(bread));


        //when
        Long orderId = orderService.addOrder(orderFormDto);

        //then
        Order savedOrder = orderRepository.save(Order.from(orderFormDto.getQuantity(), orderFormDto.getOrderDate(), orderFormDto.getOrderStatus(), user, bread));

        assertEquals( OrderStatus.ORDER, savedOrder.getOrderStatus(), "빵 주문시 상태는 ORDER");
        assertEquals( 3, savedOrder.getQuantity(), "주문한 빵 수가 일치해야한다.");
        assertEquals( stockQuantity - orderFormDto.getQuantity(), bread.getStock(), "주문 수량만큼 재고가 줄어야 한다.");
    }
}
