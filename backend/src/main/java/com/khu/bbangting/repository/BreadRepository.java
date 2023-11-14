package com.khu.bbangting.repository;

import com.khu.bbangting.model.Bread;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BreadRepository extends JpaRepository<Bread, Long> {
    List<Bread> findByStoreId(Long storeId);

    List<Bread> findByTingStatusAndAndStoreId(char tingStatus, Long storeId);
}
