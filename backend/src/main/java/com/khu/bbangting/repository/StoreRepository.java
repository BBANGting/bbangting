package com.khu.bbangting.repository;

import com.khu.bbangting.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findAllByStoreName(String storeName);
}
