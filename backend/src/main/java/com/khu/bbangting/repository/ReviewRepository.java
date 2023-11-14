package com.khu.bbangting.repository;

import com.khu.bbangting.model.Bread;
import com.khu.bbangting.model.Review;
import com.khu.bbangting.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByBread(Bread bread);

    @Modifying
    @Query("delete from Review pr where pr.user = :user")
    void deleteByUser(User user);
}
