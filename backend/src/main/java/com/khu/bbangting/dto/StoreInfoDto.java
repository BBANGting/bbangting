package com.khu.bbangting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class StoreInfoDto {

    private Long storeId;

    private String storeName;

    private int followerNum;

    private double rating;


    @Builder
    public StoreInfoDto(Long storeId, String storeName, int followerNum, double rating) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.followerNum = followerNum;
        this.rating = rating;
    }
}
