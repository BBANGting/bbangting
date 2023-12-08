package com.khu.bbangting.domain.bread.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@ToString
public class BreadInfoDto {

    private Long breadId;
    private String breadName;
    private String imgUrl;
    private String storeName;
    private char tingStatus;

    private LocalDateTime tingDateTime;
    private int maxTingNum;
    private int stock;


    @Builder
    public BreadInfoDto(Long breadId, String breadName, String imgUrl, String storeName, char tingStatus, LocalDateTime tingDateTime, int maxTingNum, int stock) {
        this.breadId = breadId;
        this.breadName = breadName;
        this.imgUrl = imgUrl;
        this.storeName = storeName;
        this.tingStatus = tingStatus;
        this.tingDateTime = tingDateTime;
        this.maxTingNum = maxTingNum;
        this.stock = stock;
    }

}
