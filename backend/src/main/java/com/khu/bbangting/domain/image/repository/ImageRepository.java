package com.khu.bbangting.domain.image.repository;

import com.khu.bbangting.domain.image.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByBreadIdOrderByIdAsc(Long breadId);

    List<Image> findAllByBreadId(Long breadId);

    Image findByBreadIdAndRepImgYn(Long breadId, char repImgYn);

    List<Image> findAllByBreadIdAndRepImgYn(Long breadId, char repImgYn);
}