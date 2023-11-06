package com.khu.bbangting.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReviewDto {

    private Long id;
    private int rating;
    private String content;
    private Timestamp createdDate;
}