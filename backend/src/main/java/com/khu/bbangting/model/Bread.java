package com.khu.bbangting.model;

import com.khu.bbangting.exception.OutOfStockException;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
@Entity
@Table(name = "breads")
public class Bread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "breadId")
    private Long id;

    @Column(nullable = false, length = 20)
    private String breadName;

    private String breadImage;

    @Column(length = 1000)
    private String content;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private Timestamp tingTime;

    @Column(nullable = false)
    private int maxTingNum;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private char tingStatus;    // 'Y', 'N'

    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;

//    //리뷰 리스트 추가
//    @OneToMany(mappedBy = "breads", fetch = FetchType.LAZY)
//    private List<Review> reviews = new ArrayList<>();

    //상품 주문 -> 상품 재고 감소
    public void subStock(int stock) {
        int restStock = this.stock - stock;

        if(restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다.");
        }
        this.stock = restStock;
    }

    //주문 취소 시 상품 개수 늘림
    public void addStock(int stock) {
        this.stock += stock;
    }

}
