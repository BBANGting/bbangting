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

    private String storeName;
    private String imgUrl;
    private int followerNum;
    private List<BreadInfoDto> breadInfoList;
    private List<BreadInfoDto> todayTingList;


    @Builder
    public MyStoreInfoDto(String storeName, String imgUrl, int followerNum, List<BreadInfoDto> breadInfoList, List<BreadInfoDto> todayTingList) {
        this.storeName = storeName;
        this.imgUrl = imgUrl;
        this.followerNum = followerNum;
        this.breadInfoList = breadInfoList;
        this.todayTingList = todayTingList;
    }

}
