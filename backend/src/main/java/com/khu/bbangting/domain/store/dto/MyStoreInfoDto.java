package com.khu.bbangting.domain.store.dto;

import com.khu.bbangting.domain.bread.model.Bread;
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

    private List<Bread> breadList;
    private List<Bread> tingList;


    @Builder
    public MyStoreInfoDto(Long storeId, String storeName, String imgUrl, int followerNum, List<Bread> breadList, List<Bread> tingList) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.imgUrl = imgUrl;
        this.followerNum = followerNum;
        this.breadList = breadList;
        this.tingList = tingList;
    }

}