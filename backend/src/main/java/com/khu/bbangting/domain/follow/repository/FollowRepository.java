package com.khu.bbangting.domain.follow.repository;

import com.khu.bbangting.domain.follow.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByStoreIdAndUserId(Long storeId, Long userId);

    List<Follow> findAllByUserId(Long userId);

    List<Follow> findAllByStoreId(Long storeId);
}
