package com.khu.bbangting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Store {

    @Id
    @Column(name = "storeId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String storeName;     // 스토어명

    private String storeLogo;     // 로고

    private String storeImage;    // 배경 이미지

    private String introduction;  // 스토어 소개

    @Column(nullable = false)
    private String location;      // 가게 위치

    @OneToOne(mappedBy = "user")
    private User user;

    @OneToMany(mappedBy = "bread")
    private List<Bread> breads = new ArrayList<>();

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long followerNum;
}
