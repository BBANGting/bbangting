package com.khu.bbangting.repository;

import com.khu.bbangting.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}