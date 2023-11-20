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

    private String storeName;

    private int followerNum;

    private List<Bread> breadList;

    private List<Bread> tingList;


    @Builder
    public MyStoreInfoDto(String storeName, int followerNum, List<Bread> breadList, List<Bread> tingList) {
        this.storeName = storeName;
        this.followerNum = followerNum;
        this.breadList = breadList;
        this.tingList = tingList;
    }

}