package com.khu.bbangting.model;

import com.khu.bbangting.dto.BreadFormDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "breads")
@NoArgsConstructor
@AllArgsConstructor
@Getter
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

    @Column(nullable = false)
    private String tingTime;

    @Column(nullable = false)
    private int maxTingNum;

    @Column
    private int stock;

    @Column(nullable = false)
    private char tingStatus;    // 'Y', 'N'

    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;

    @Builder
    private Bread(Store store, String breadName, String description, int price, String tingTime, int maxTingNum, int stock, char tingStatus) {
        this.store = store;
        this.breadName = breadName;
        this.description = description;
        this.price = price;
        this.tingTime = tingTime;
        this.maxTingNum = maxTingNum;
        this.stock = stock;               // *** 이후 재고 변동될 시, 문제 없는지 체크 필요
        this.tingStatus = tingStatus;
    }

    public void update(BreadFormDto requestDto) {
        this.breadName = requestDto.getBreadName();
        this.description = requestDto.getDescription();
        this.price = requestDto.getPrice();
        this.tingTime = requestDto.getTingTime();
        this.maxTingNum = requestDto.getMaxTingNum();
        this.tingStatus = requestDto.getTingStatus();
    }

}
