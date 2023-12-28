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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    // 빵 세부 페이지 리뷰 모음
    @Override
    public List<ReviewFormDto> getListOfBreadReview(Long breadId) {

        Bread bread = Bread.builder().id(breadId).build();

        List<Review> result = reviewRepository.findByBread(bread);

        return result.stream().map(breadReview -> fromReview(breadReview)).collect(Collectors.toList());
    }

    // 유저 마이페이지 리뷰 모음
    @Override
    public List<ReviewFormDto> getListOfUserReview(Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Review> result = reviewRepository.findByUserId(user.getId());

        return result.stream().map(userReview -> fromReview(userReview)).collect(Collectors.toList());
    }

    // 리뷰 작성
    @Override
    public ReviewFormDto register(User user, Bread bread, @Valid ReviewFormDto requestDto) {

        if (reviewRepository.existsByUserIdAndBreadId(user.getId(), bread.getId())) {
            throw new CustomException(ErrorCode.REVIEW_IS_EXIST);}

        return fromReview(reviewRepository.save(toEntity(user, bread, requestDto)));
    }

    // 리뷰 수정
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

    // 리뷰 삭제
    @Transactional
    public void remove(Long reviewId) {

        reviewRepository.deleteById(reviewId);
    }
}
