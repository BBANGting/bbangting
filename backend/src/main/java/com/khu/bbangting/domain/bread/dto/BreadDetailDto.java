package com.khu.bbangting.domain.bread.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@ToString
public class BreadDetailDto {

    private Long breadId;
    private String breadName;
    private int price;
    private String tingDateTime;
    private int stock;
    private char tingStatus;
    private String storeName;
    private String location;

    @Builder
    BreadDetailDto(Long breadId, String breadName, int price, LocalDateTime tingDateTime, int stock, char tingStatus, String storeName, String location) {
        this.breadId = breadId;
        this.breadName = breadName;
        this.price = price;
        this.tingDateTime = String.valueOf(tingDateTime);
        this.stock = stock;
        this.tingStatus = tingStatus;
        this.storeName = storeName;
        this.location = location;
    }
}