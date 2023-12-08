package com.khu.bbangting.domain.bread.repository;

import com.khu.bbangting.domain.bread.model.Bread;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BreadRepository extends JpaRepository<Bread, Long> {
    List<Bread> findAllByStoreId(Long storeId);

    List<Bread> findAllByTingStatus(char tingStatus);

    List<Bread> findAllByTingStatusNot(char tingStatus);

    List<Bread> findAllByTingStatusOrderByTingDateTime(char tingStatus);

    List<Bread> findByTingStatusAndAndStoreId(char tingStatus, Long storeId);

    List<Bread> findByTingStatusOrderByStore(char tingStatus);

    List<Bread> findByTingStatusOrderByTingDateTime(char tingStatus);
}
