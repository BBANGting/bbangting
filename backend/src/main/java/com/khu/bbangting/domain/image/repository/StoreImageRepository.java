package com.khu.bbangting.domain.image.repository;

import com.khu.bbangting.domain.image.model.StoreImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreImageRepository extends JpaRepository<StoreImage, Long> {

    List<StoreImage> findAllByStoreId(Long id);

    List<StoreImage> findByStoreIdOrderByIdAsc(Long storeId);

    Optional<StoreImage> findByStoreIdAndLogoImgYn(Long storeId, char logoImgYn);
}
