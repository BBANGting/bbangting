package com.khu.bbangting.domain.review.dto;

import com.khu.bbangting.domain.review.model.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUpdateFormDto {

    private double rating;
    private String content;

    public static ReviewUpdateFormDto fromReview(Review review) {
        return new ReviewUpdateFormDto(review.getRating(), review.getContent());
    }
}
