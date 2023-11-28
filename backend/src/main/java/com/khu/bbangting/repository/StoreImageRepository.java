package com.khu.bbangting.repository;

import com.khu.bbangting.model.StoreImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreImageRepository extends JpaRepository<StoreImage, Long> {

    List<StoreImage> findAllByStoreId(Long id);

    List<StoreImage> findByStoreIdOrderByIdAsc(Long storeId);

    StoreImage findByStoreIdAndLogoImgYn(Long storeId, char logoImgYn);
}
