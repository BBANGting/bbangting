package com.khu.bbangting.domain.review.repository;

import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.review.model.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByBread(Bread bread);

    boolean existsByUserIdAndBreadId(Long userId, Long breadId);
}
