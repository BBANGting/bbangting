package com.khu.bbangting.domain.bread.model;

import com.khu.bbangting.domain.bread.dto.BreadFormDto;
import com.khu.bbangting.domain.order.model.Order;
import com.khu.bbangting.domain.store.model.Store;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "breads")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class Bread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "breadId")
    private Long id;

    @Column(nullable = false, length = 20)
    private String breadName;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int maxTingNum;

    @Column
    private int stock;

    @Column(nullable = false)
    private char tingStatus;    // 'O', 'C', 'E', 'N'

    // 알림 기능
    @CreationTimestamp
    private Timestamp publishedDateTime;

    @Column(nullable = false)
    private LocalDateTime tingDateTime;

    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;

    @OneToMany(mappedBy = "bread") @ToString.Exclude
    @OrderBy("orderDate")
    private List<Order> orders = new ArrayList<>();

    @Builder
    private Bread(Store store, String breadName, String description, int price, int maxTingNum, int stock, char tingStatus,
                  Timestamp publishedDateTime, LocalDateTime tingDateTime) {
        this.store = store;
        this.breadName = breadName;
        this.description = description;
        this.price = price;
        this.maxTingNum = maxTingNum;
        this.stock = stock;               // *** 이후 재고 변동될 시, 문제 없는지 체크 필요
        this.tingStatus = tingStatus;
        this.publishedDateTime = publishedDateTime;
        this.tingDateTime = tingDateTime;
    }

    public void update(BreadFormDto requestDto) {
        this.breadName = requestDto.getBreadName();
        this.description = requestDto.getDescription();
        this.price = requestDto.getPrice();
        this.maxTingNum = requestDto.getMaxTingNum();
        this.tingDateTime = requestDto.getTingDateTime();
    }

    public void updateTingStatus(char tingStatus) {
        this.tingStatus = tingStatus;
    }

    public boolean isTingTime() {
        return LocalDateTime.now().isAfter(this.tingDateTime) && !isSoldOut();
    }

    public boolean isSoldOut() {
        return this.stock == 0;
    }

    public void removeStock(int quantity) {
        int reStock = this.stock - quantity;
        if (reStock < 0) {
            throw new CustomException(ErrorCode.BREAD_SOLD_OUT);
        }
        this.stock = reStock;
    }

    public void addStock(int quantity) {
        this.stock += quantity;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        order.attach(this);
    }

    public void removeOrder(Order order) {
        this.orders.remove(order);
        order.detach();
    }

    // 빵팅 열리지 않은 상태 -> react 오픈예정 버튼 <button th:if="${bread.isNotTingTime(#authentication.principal)}"
    public boolean isNotTingTimeFor(User user) {
        return !isTingTime();
    }

    // 빵팅 주문 가능 상태 -> react 구매 버튼 <button th:if="${bread.isOrderFor(#authentication.principal)}"
    public boolean isOrderFor(User user) {
        return isTingTime() && !isSoldOut() && !isAlreadyOrdered(user);
    }

    // 빵팅 주문 완료 상태 -> react 구매 완료 버튼 <button th:if="${bread.isNotOrderFor(#authentication.principal)}"
    public boolean isAlreadyOrderFor(User user) {
        return isAlreadyOrdered(user);
    }

    // 빵팅 품절 상태 -> react 품절 버튼 <button th:if="${bread.usNotTingTime(#authentication.principal)}"
    public boolean isNotStock(User user) {
        return isNotStock(user);
    }

    public boolean isAlreadyOrdered(User user) {

        for (Order order : this.orders) {
            if (order.getUser().equals(user)) {
                return true;
            }
        }
        return false;
    }

}
