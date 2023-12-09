package com.khu.bbangting.domain.bread.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@ToString
public class BreadSaleDto {

    private Long breadId;
    private String breadName;
    private String storeName;
    private String imgUrl;
    private int price;
    private LocalDateTime tingDateTime;
    private int stock;
    private char tingStatus;

    @Builder
    BreadSaleDto(Long breadId, String breadName, String storeName, String imgUrl, int price, LocalDateTime tingDateTime, int stock, char tingStatus) {
        this.breadId = breadId;
        this.breadName = breadName;
        this.storeName = storeName;
        this.imgUrl = imgUrl;
        this.price = price;
        this.tingDateTime = tingDateTime;
        this.stock = stock;
        this.tingStatus = tingStatus;
    }
}
