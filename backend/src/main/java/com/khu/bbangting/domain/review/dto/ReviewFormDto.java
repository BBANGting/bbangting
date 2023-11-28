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
public class ReviewFormDto {

    private String nickname;
    private double rating;
    private String content;

    public static ReviewFormDto fromReview(Review review) {
        return new ReviewFormDto(review.getUser().getNickname(), review.getRating(), review.getContent());
    }

}
