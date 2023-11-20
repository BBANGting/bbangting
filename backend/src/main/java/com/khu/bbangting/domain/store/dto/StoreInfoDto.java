package com.khu.bbangting.domain.store.dto;
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

    private String location;

    private int followerNum;

    private double rating;


    @Builder
    public StoreInfoDto(Long storeId, String storeName, String location, int followerNum, double rating) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.location = location;
        this.followerNum = followerNum;
        this.rating = rating;
    }
}