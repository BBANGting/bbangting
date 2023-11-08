package com.khu.bbangting.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Timestamp;

@Entity
@Table(name = "breads")
@NoArgsConstructor
@AllArgsConstructor
@Getter
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

    @ColumnDefault("'Y'")
    private char tingStatus;    // 'Y', 'N'

    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;

    @Builder
    private Bread(String breadName, String description, int price, String tingTime, int maxTingNum) {
        this.breadName = breadName;
        this.description = description;
        this.price = price;
        this.tingTime = tingTime;
        this.maxTingNum = maxTingNum;
        this.stock = maxTingNum;              // *** 이후 재고 변동될 시, 문제 없는지 체크 필요
    }
}
