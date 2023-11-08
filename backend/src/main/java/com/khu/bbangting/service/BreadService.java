package com.khu.bbangting.service;

import com.khu.bbangting.dto.BreadFormDto;
import com.khu.bbangting.model.Bread;
import com.khu.bbangting.repository.BreadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BreadService {

    private final BreadRepository breadRepository;

//    @Autowired
//    private ImageService imageService;


    public Long saveBread(BreadFormDto breadFormDto) throws Exception {

        // 빵 등록
        Bread bread = breadFormDto.newBread();
        breadRepository.save(bread);

//        // 이미지 등록
//        for (int i = 0; i < imageFileList.size(); i++) {
//            Image image = new Image();
//            image.setBread(bread);
//
//            imageService.saveImage(image, imageFileList.get(i));
//        }

        return bread.getId();
    }
}
