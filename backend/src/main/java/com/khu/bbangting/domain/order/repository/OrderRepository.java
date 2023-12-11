package com.khu.bbangting.domain.order.repository;

import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.order.model.Order;
import com.khu.bbangting.domain.order.model.OrderStatus;
import com.khu.bbangting.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);

    boolean existsByBreadAndUser(Bread bread, User user);

    Order findByUserIdAndBreadId(Long userId, Long breadId);
}
