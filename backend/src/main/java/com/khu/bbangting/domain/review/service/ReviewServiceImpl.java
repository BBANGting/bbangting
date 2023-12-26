package com.khu.bbangting.domain.review.service;

import com.khu.bbangting.domain.review.dto.ReviewFormDto;
import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.review.dto.ReviewUpdateFormDto;
import com.khu.bbangting.domain.review.model.Review;
import com.khu.bbangting.domain.review.repository.ReviewRepository;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewFormDto> getListOfBread(Long breadId) {

        Bread bread = Bread.builder().id(breadId).build();

        List<Review> result = reviewRepository.findByBread(bread);

        return result.stream().map(breadReview -> fromReview(breadReview)).collect(Collectors.toList());
    }

    @Override
    public ReviewFormDto register(User user, Bread bread, @Valid ReviewFormDto requestDto) {

        User userDetail = userRepository.findByEmail(user.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (reviewRepository.existsByUserIdAndBreadId(userDetail.getId(), bread.getId())) {
            throw new CustomException(ErrorCode.REVIEW_IS_EXIST);}

        return fromReview(reviewRepository.save(toEntity(userDetail, bread, requestDto)));
    }

    @Override
    public ReviewUpdateFormDto modify(User user, Long reviewId, ReviewUpdateFormDto requestDto) {

        return reviewRepository.findById(reviewId)
                .map(review -> {
                    Review updatedReview = review.update(
                            requestDto.getRating(),
                            requestDto.getContent()
                    );
                    reviewRepository.save(updatedReview);
                    return ReviewUpdateFormDto.fromReview(updatedReview);
                })
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

    }

    @Transactional
    public void remove(Long reviewId) {

        reviewRepository.deleteById(reviewId);
    }
}
