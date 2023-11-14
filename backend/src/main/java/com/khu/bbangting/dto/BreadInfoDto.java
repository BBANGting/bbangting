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

    private String storeName;

    private String tingTime;

    private int stock;


    @Builder
    public BreadInfoDto(Long breadId, String breadName, String storeName, String tingTime, int stock) {
        this.breadId = breadId;
        this.breadName = breadName;
        this.storeName = storeName;
        this.tingTime = tingTime;
        this.stock = stock;
    }

}
