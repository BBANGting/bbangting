package com.khu.bbangting.service;

import com.khu.bbangting.model.Image;
import com.khu.bbangting.repository.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {

    @Value("${imageLocation}")
    private String imageLocation;

    private final ImageRepository imageRepository;

    private final FileService fileService;

    public void saveImage(Image image, MultipartFile imageFile) throws Exception {
        String oriImageName = imageFile.getOriginalFilename();
        String imageName = "";
        String imageUrl = "";

        // 파일 업로드
        if (!StringUtils.isEmpty(oriImageName)) {
            imageName = fileService.uploadFile(imageLocation, oriImageName, imageFile.getBytes());
            imageUrl = "/images/bread/" + imageName;
        }

        // 상품 이미지 정보 저장
        image.updateImage(oriImageName, imageName, imageUrl);
        imageRepository.save(image);
    }

    public void updateImage(Long imageId, MultipartFile imageFile) throws Exception {
        if (!imageFile.isEmpty()) {
            Image savedImage = imageRepository.findById(imageId)
                    .orElseThrow(EntityNotFoundException::new);

            // 기존 이미지 파일 삭제
            if (!StringUtils.isEmpty(savedImage.getImageName())) {
                fileService.deleteFile(imageLocation+"/"+savedImage.getImageName());
            }

            String oriImageName = imageFile.getOriginalFilename();
            String imageName = fileService.uploadFile(imageLocation, oriImageName, imageFile.getBytes());
            String imageUrl = "/images/bread/" + imageName;
            savedImage.updateImage(oriImageName, imageName, imageUrl);
        }
    }

}
