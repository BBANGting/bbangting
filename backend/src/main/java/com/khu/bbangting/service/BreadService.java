package com.khu.bbangting.service;

import com.khu.bbangting.dto.BreadFormDto;
import com.khu.bbangting.dto.BreadUpdateFormDto;
import com.khu.bbangting.model.Bread;
import com.khu.bbangting.model.Store;
import com.khu.bbangting.repository.BreadRepository;
import com.khu.bbangting.repository.StoreRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BreadService {

    private final BreadRepository breadRepository;

    private final StoreRepository storeRepository;

//    @Autowired
//    private ImageService imageService;


    public void save(BreadFormDto requestDto) {

        Store store = storeRepository.findById(requestDto.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("해당 스토어가 존재하지 않습니다. id = " + requestDto.getStoreId()));

//        // 이미지 등록
//        for (int i = 0; i < imageFileList.size(); i++) {
//            Image image = new Image();
//            image.setBread(bread);
//
//            imageService.saveImage(image, imageFileList.get(i));
//        }

        breadRepository.save(requestDto.toEntity(store));
    }

    public void delete(Long breadId) {

        Bread bread = breadRepository.findById(breadId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 빵이 존재하지 않습니다. id = " + breadId));

        breadRepository.delete(bread);

    }

}
