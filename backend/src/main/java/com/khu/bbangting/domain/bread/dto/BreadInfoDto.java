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

    private String storeName;

    private LocalDateTime tingDateTime;

    private int stock;


    @Builder
    public BreadInfoDto(Long breadId, String breadName, String storeName, LocalDateTime tingDateTime, int stock) {
        this.breadId = breadId;
        this.breadName = breadName;
        this.storeName = storeName;
        this.tingDateTime = tingDateTime;
        this.stock = stock;
    }

}