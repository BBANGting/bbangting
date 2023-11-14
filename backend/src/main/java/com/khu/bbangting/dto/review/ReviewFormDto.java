package com.khu.bbangting.dto.review;

import com.khu.bbangting.model.*;
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

    private Long reviewId;

    private Long breadId;

    private Long userId;

    private String username;

    private int rating;

    private String content;

}
