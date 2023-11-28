package com.khu.bbangting.domain.review.service;

import com.khu.bbangting.domain.review.dto.ReviewFormDto;
import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.review.dto.ReviewUpdateFormDto;
import com.khu.bbangting.domain.review.model.Review;
import com.khu.bbangting.domain.review.repository.ReviewRepository;
import com.khu.bbangting.domain.user.dto.PasswordUpdateDto;
import com.khu.bbangting.domain.user.dto.UserUpdateDto;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewFormDto> getListOfBread(Long breadId) {

        Bread bread = Bread.builder().id(breadId).build();

        List<Review> result = reviewRepository.findByBread(bread);

        return result.stream().map(breadReview -> fromReview(breadReview)).collect(Collectors.toList());
    }

    @Override
    public ReviewFormDto register(User user, Bread bread, @Valid ReviewFormDto request) {

        if (reviewRepository.existsByUserIdAndBreadId(user.getId(), bread.getId())) {
            throw new CustomException(ErrorCode.REVIEW_IS_EXIST);}

        Review breadReview = toEntity(user, bread, request);
        reviewRepository.save(breadReview);

        return ReviewFormDto.fromReview(breadReview);
    }

    @Override
    public ReviewUpdateFormDto modify(User user, Long reviewId, @Valid ReviewUpdateFormDto request) {

        Optional<Review> result = reviewRepository.findById(reviewId);

        if (result.isPresent()) {

            Review breadReview = result.get();
            breadReview.changeRating(request.getRating());
            breadReview.changeContent(request.getContent());

            reviewRepository.save(breadReview);

            return ReviewUpdateFormDto.fromReview(breadReview);
        }
        else {
            throw new CustomException(ErrorCode.REVIEW_IS_EXIST);
        }

    }

    @Override
    public void remove(Long reviewId) {

        reviewRepository.deleteById(reviewId);
    }
}
