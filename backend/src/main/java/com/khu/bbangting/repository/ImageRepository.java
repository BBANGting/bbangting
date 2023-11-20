package com.khu.bbangting.repository;

import com.khu.bbangting.model.Bread;
import com.khu.bbangting.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByBreadIdOrderByIdAsc(Long breadId);

    List<Image> findAllByBreadId(Long breadId);
}