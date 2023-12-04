package com.khu.bbangting.domain.review.dto;

import com.khu.bbangting.domain.review.model.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewFormDto {

    private String nickname;
    private double rating;
    private String content;

}
