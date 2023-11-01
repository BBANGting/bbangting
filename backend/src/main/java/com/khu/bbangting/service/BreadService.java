package com.khu.bbangting.service;

import com.khu.bbangting.dto.BreadFormDto;
import com.khu.bbangting.model.Bread;
import com.khu.bbangting.model.Image;
import com.khu.bbangting.repository.BreadRepository;
import com.khu.bbangting.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BreadService {

    @Autowired
    private BreadRepository breadRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageRepository imageRepository;

    @Transactional
    public Long saveBread(BreadFormDto breadFormDto, List<MultipartFile> imageFileList) throws Exception {

        // 빵 등록
        Bread bread = breadFormDto.newBread();
        breadRepository.save(bread);

        // 이미지 등록
        for (int i = 0; i < imageFileList.size(); i++) {
            Image image = new Image();
            image.setBread(bread);

            imageService.saveImage(image, imageFileList.get(i));
        }

        return bread.getId();
    }
}
