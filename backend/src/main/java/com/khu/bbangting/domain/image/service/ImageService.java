//package com.khu.bbangting.domain.image.service;
//
//import com.khu.bbangting.domain.image.model.Image;
//import com.khu.bbangting.domain.image.repository.ImageRepository;
//import com.khu.bbangting.domain.image.model.StoreImage;
//import com.khu.bbangting.domain.image.repository.StoreImageRepository;
//import com.khu.bbangting.domain.store.model.Store;
//import jakarta.persistence.EntityNotFoundException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//import org.thymeleaf.util.StringUtils;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class ImageService {
//
//    @Value("${imageLocation}")
//    private String imageLocation;
//    @Value("${storeImageLocation}")
//    private String storeImageLocation;
//
//    private final ImageRepository imageRepository;
//    private final StoreImageRepository storeImageRepository;
//    private final FileService fileService;
//
//    public void saveImage(Image image, MultipartFile imageFile) throws Exception {
//        String oriImageName = imageFile.getOriginalFilename();
//        String imageName = "";
//        String imageUrl = "";
//
//        // 파일 업로드
//        if (!StringUtils.isEmpty(oriImageName)) {
//            imageName = fileService.uploadFile(imageLocation, oriImageName, imageFile.getBytes());
//            imageUrl = "/images/bread/" + imageName;
//        }
//
//        // 상품 이미지 정보 저장
//        image.updateImage(oriImageName, imageName, imageUrl);
//        imageRepository.save(image);
//    }
//
//    public void updateImage(Long imageId, MultipartFile imageFile) throws Exception {
//        if (!imageFile.isEmpty()) {
//            Image savedImage = imageRepository.findById(imageId)
//                    .orElseThrow(EntityNotFoundException::new);
//
//            // 기존 이미지 파일 삭제
//            if (!StringUtils.isEmpty(savedImage.getImageName())) {
//                fileService.deleteFile(imageLocation+"/"+savedImage.getImageName());
//            }
//
//            String oriImageName = imageFile.getOriginalFilename();
//            String imageName = fileService.uploadFile(imageLocation, oriImageName, imageFile.getBytes());
//            String imageUrl = "/images/bread/" + imageName;
//            savedImage.updateImage(oriImageName, imageName, imageUrl);
//        }
//    }
//
//    public void saveStoreImage(StoreImage storeImage, MultipartFile imageFile) throws Exception {
//        String oriImageName = imageFile.getOriginalFilename();
//        String imageName = "";
//        String imageUrl = "";
//
//        // 파일 업로드
//        if (!StringUtils.isEmpty(oriImageName)) {
//            imageName = fileService.uploadFile(storeImageLocation, oriImageName, imageFile.getBytes());
//            imageUrl = "/images/store/" + imageName;
//        }
//
//        // 스토어 이미지 정보 저장
//        storeImage.updateImage(oriImageName, imageName, imageUrl);
//        storeImageRepository.save(storeImage);
//    }
//
//    public void updateStoreImage(Store store, Long storeImageId, MultipartFile imageFile) throws Exception {
//        if (!imageFile.isEmpty()) {
//
//            // 기존 이미지 파일 삭제
//            if (storeImageId != 0L) {
//                StoreImage savedStoreImage = storeImageRepository.findById(storeImageId)
//                        .orElseThrow(EntityNotFoundException::new);
//
//                if (!StringUtils.isEmpty(savedStoreImage.getImageName())) {
//                    fileService.deleteFile(storeImageLocation + "/" + savedStoreImage.getImageName());
//                }
//
//                String oriImageName = imageFile.getOriginalFilename();
//                String imageName = fileService.uploadFile(storeImageLocation, oriImageName, imageFile.getBytes());
//                String imageUrl = "/images/store/" + imageName;
//                savedStoreImage.updateImage(oriImageName, imageName, imageUrl);
//
//                // 새로운 이미지 등록
//            } else {
//                StoreImage storeImage = new StoreImage();
//                storeImage.setStore(store);
//                storeImage.setLogoImgYn('N');     // 나머지 사진
//
//                saveStoreImage(storeImage, imageFile);
//            }
//        }
//    }
//
//    public void deleteStoreImage(Long storeImageId) throws Exception {
//        StoreImage savedStoreImage = storeImageRepository.findById(storeImageId)
//                .orElseThrow(EntityNotFoundException::new);
//
//        if (!StringUtils.isEmpty(savedStoreImage.getImageName())) {
//            fileService.deleteFile(storeImageLocation + "/" + savedStoreImage.getImageName());
//        }
//
//        storeImageRepository.delete(savedStoreImage);
//    }
//
//}