package com.khu.bbangting.domain.store.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class MyStoreInfoDto {

    private Long storeId;
    private String storeName;
    private String imgUrl;
    private int followerNum;


    @Builder
    public MyStoreInfoDto(Long storeId, String storeName, String imgUrl, int followerNum) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.imgUrl = imgUrl;
        this.followerNum = followerNum;
    }

}
