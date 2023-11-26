package com.khu.bbangting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class BreadDetailDto {

    private Long breadId;
    private String breadName;
    private String imgUrl;
    private int price;
    private String tingTime;
    private int stock;
    private char tingStatus;
    private String storeName;

    @Builder
    BreadDetailDto(Long breadId, String breadName, String imgUrl, int price, String tingTime, int stock, char tingStatus, String storeName) {
        this.breadId = breadId;
        this.breadName = breadName;
        this.imgUrl = imgUrl;
        this.price = price;
        this.tingTime = tingTime;
        this.stock = stock;
        this.tingStatus = tingStatus;
        this.storeName = storeName;
    }
}
