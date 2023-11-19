package com.khu.bbangting.repository;

import com.khu.bbangting.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findAllByStoreName(String storeName);

    Optional<Store> findByUserId(Long userId);
}
