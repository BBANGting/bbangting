package com.khu.bbangting.domain.bread.model;

import com.khu.bbangting.domain.bread.dto.BreadFormDto;
import com.khu.bbangting.domain.store.model.Store;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "breads")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Bread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "breadId")
    private Long id;

    @Column(nullable = false, length = 20)
    private String breadName;

//    private String breadImage;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private int price;

//    @Column(nullable = false)
//    private LocalDateTime tingTime;

    @Column(nullable = false)
    private int maxTingNum;

    @Column
    private int stock;

    @Column(nullable = false)
    private char tingStatus;    // 'Y', 'N'

    // 알림 기능
    private LocalDateTime publishedDateTime;
    private LocalDateTime tingDateTime;
    private boolean publishTing;
    private boolean startTing;

    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;

    @Builder
    private Bread(Store store, String breadName, String description, int price, int maxTingNum, int stock, char tingStatus,
                    LocalDateTime publishedDateTime, LocalDateTime tingDateTime, boolean publishTing, boolean startTing) {
        this.store = store;
        this.breadName = breadName;
        this.description = description;
        this.price = price;
        this.maxTingNum = maxTingNum;
        this.stock = stock;               // *** 이후 재고 변동될 시, 문제 없는지 체크 필요
        this.tingStatus = tingStatus;
        this.publishedDateTime = publishedDateTime;
        this.tingDateTime = tingDateTime;
        this.publishTing = publishTing;
        this.startTing = startTing;
    }

    public void update(BreadFormDto requestDto) {
        this.breadName = requestDto.getBreadName();
        this.description = requestDto.getDescription();
        this.price = requestDto.getPrice();
        this.maxTingNum = requestDto.getMaxTingNum();
        this.tingStatus = requestDto.getTingStatus();
        this.publishedDateTime = LocalDateTime.now();
        this.tingDateTime = LocalDateTime.parse(requestDto.getTingDateTime());
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

    public void publishTing() {
        this.publishTing = true;
        this.publishedDateTime = LocalDateTime.now();
    }

    public void startTing() {
        if (isTingTime() && publishTing) {
            this.startTing = true;
        }
        if (stock > 0 && publishTing) {
            this.startTing = true;
        }
    }

}