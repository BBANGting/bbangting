package com.khu.bbangting.service;

import com.khu.bbangting.dto.bread.BreadFormDto;
import com.khu.bbangting.dto.bread.BreadUpdateFormDto;
import com.khu.bbangting.model.Bread;
import com.khu.bbangting.model.Store;
import com.khu.bbangting.repository.BreadRepository;
import com.khu.bbangting.repository.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BreadService {

    private final BreadRepository breadRepository;

    private final StoreRepository storeRepository;

//    @Autowired
//    private ImageService imageService;


    // 등록된 상품 정보 불러오기
    public BreadFormDto getBreadForm(Long breadId) {

        Bread bread = breadRepository.findById(breadId)
                .orElseThrow(EntityNotFoundException::new);

        return BreadFormDto.builder()
                .storeId(bread.getStore().getId())
                .breadName(bread.getBreadName())
                .description(bread.getDescription())
                .price(bread.getPrice())
                .tingTime(String.valueOf(bread.getTingTime()))
                .maxTingNum(bread.getMaxTingNum())
                .tingStatus(bread.getTingStatus()).build();

    }

    public void saveBread(BreadFormDto requestDto) {

        Store store = storeRepository.findById(requestDto.getStoreId())
                .orElseThrow(() -> new EntityNotFoundException("해당 스토어가 존재하지 않습니다. id = " + requestDto.getStoreId()));

//        // 이미지 등록
//        for (int i = 0; i < imageFileList.size(); i++) {
//            Image image = new Image();
//            image.setBread(bread);
//
//            imageService.saveImage(image, imageFileList.get(i));
//        }

        breadRepository.save(requestDto.toEntity(store));
    }

    public void deleteBread(Long breadId) {

        Bread bread = breadRepository.findById(breadId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 빵이 존재하지 않습니다. id = " + breadId));

        breadRepository.delete(bread);
    }

    public void updateBread(Long breadId, BreadUpdateFormDto requestDto) {
        Bread bread = breadRepository.findById(breadId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 빵이 존재하지 않습니다. id = " + breadId));

        bread.update(requestDto);
        log.info(bread.toString());
    }

}