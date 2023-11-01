package com.khu.bbangting.service;

import com.khu.bbangting.model.Image;
import com.khu.bbangting.repository.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${imageLocation}")
    private String ImageLocation;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private FileService fileService;

    @Transactional
    public void saveImage(Image image, MultipartFile ImageFile) throws Exception {
        String oriImageName = ImageFile.getOriginalFilename();
        String imageName = "";
        String imageUrl = "";

        // 파일 업로드
        if (!StringUtils.isEmpty(oriImageName)) {
            imageName = fileService.uploadFile(ImageLocation, oriImageName, ImageFile.getBytes());
            imageUrl = "images/bbang/" + imageName;
        }

        // 상품 이미지 정보 저장
        image.updateImage(oriImageName, imageName, imageUrl);
        imageRepository.save(image);
    }

    @Transactional
    public void updateImage(Long imageId, MultipartFile imageFile) throws Exception {
        if (!imageFile.isEmpty()) {
            Image savedImage = imageRepository.findById(imageId)
                    .orElseThrow(EntityNotFoundException::new);

            // 기존 이미지 파일 삭제
            if (!StringUtils.isEmpty(savedImage.getImageName())) {
                fileService.deleteFile(ImageLocation+"/"+savedImage.getImageName());
            }

            String oriImageName = imageFile.getOriginalFilename();
            String imageName = fileService.uploadFile(ImageLocation, oriImageName, imageFile.getBytes());
            String imageUrl = "/images/bbang/" + imageName;
            savedImage.updateImage(oriImageName, imageName, imageUrl);
        }
    }

}
