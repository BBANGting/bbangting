package com.khu.bbangting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class BreadInfoDto {

    private Long breadId;
    private String breadName;
    private String imgUrl;
    private String storeName;
    private String tingTime;
    private int maxTingNum;
    private int stock;


    @Builder
    public BreadInfoDto(Long breadId, String breadName, String imgUrl, String storeName, String tingTime, int maxTingNum, int stock) {
        this.breadId = breadId;
        this.breadName = breadName;
        this.imgUrl = imgUrl;
        this.storeName = storeName;
        this.tingTime = tingTime;
        this.maxTingNum = maxTingNum;
        this.stock = stock;
    }

}
