package com.khu.bbangting.dto;

import com.khu.bbangting.model.Bread;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

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
