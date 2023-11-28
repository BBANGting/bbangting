package com.khu.bbangting.domain.bread.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@ToString
public class BreadDetailDto {

    private Long breadId;
    private String breadName;
    private String imgUrl;
    private int price;
    private String tingDateTime;
    private int stock;
    private char tingStatus;
    private String storeName;

    @Builder
    BreadDetailDto(Long breadId, String breadName, String imgUrl, int price, LocalDateTime tingDateTime, int stock, char tingStatus, String storeName, String location) {
        this.breadId = breadId;
        this.breadName = breadName;
        this.imgUrl = imgUrl;
        this.price = price;
        this.tingDateTime = String.valueOf(tingDateTime);
        this.stock = stock;
        this.tingStatus = tingStatus;
        this.storeName = storeName;
    }
}