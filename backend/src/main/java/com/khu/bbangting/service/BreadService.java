package com.khu.bbangting.service;

import com.khu.bbangting.dto.*;
import com.khu.bbangting.dto.BreadDetailDto;
import com.khu.bbangting.dto.BreadFormDto;
import com.khu.bbangting.dto.BreadInfoDto;
import com.khu.bbangting.model.Bread;
import com.khu.bbangting.model.Image;
import com.khu.bbangting.model.Store;
import com.khu.bbangting.repository.BreadRepository;
import com.khu.bbangting.repository.ImageRepository;
import com.khu.bbangting.repository.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BreadService {

    private final BreadRepository breadRepository;
    private final StoreRepository storeRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;


    @Transactional(readOnly = true)
    // 등록된 빵 정보 불러오기
    public BreadFormDto getBreadForm(Long breadId) {
        List<Image> breadImgList = imageRepository.findByBreadIdOrderByIdAsc(breadId);
        List<ImageDto> breadImgDtoList = new ArrayList<>();
        List<Long> imageIds = new ArrayList<>();

        for (Image image : breadImgList) {
            ImageDto imageDto = ImageDto.of(image);
            breadImgDtoList.add(imageDto);
            imageIds.add(image.getId());
        }

        Bread bread = breadRepository.findById(breadId)
                .orElseThrow(() -> new EntityNotFoundException("해당 스토어가 존재하지 않습니다. id = " + breadId));

        BreadFormDto breadFormDto = BreadFormDto.builder()
                .storeId(bread.getStore().getId())
                .breadName(bread.getBreadName())
                .description(bread.getDescription())
                .price(bread.getPrice())
                .tingTime(bread.getTingTime())
                .maxTingNum(bread.getMaxTingNum())
                .tingStatus(bread.getTingStatus()).build();

        breadFormDto.setImageDtoList(breadImgDtoList);
        breadFormDto.setImageIds(imageIds);

        return breadFormDto;

    }

    // 오늘의 빵팅 목록 불러오기
    public List<BreadInfoDto> getTodayTing() {
        List<Bread> breadList = breadRepository.findByTingStatusOrderByStore('Y');

        List<BreadInfoDto> breadInfoDtoList = new ArrayList<>();

        for (Bread bread : breadList) {
            BreadInfoDto breadInfoDto = BreadInfoDto.builder()
                    .breadId(bread.getId())
                    .breadName(bread.getBreadName())
                    .storeName(bread.getStore().getStoreName())
                    .tingTime(bread.getTingTime())
                    .stock(bread.getStock()).build();

            breadInfoDtoList.add(breadInfoDto);
        }

        return breadInfoDtoList;
    }

    // 오픈 예정 빵 목록 불러오기
    public List<BreadInfoDto> getOpenLineUp() {
        List<Bread> breadList = breadRepository.findAll();

        List<BreadInfoDto> breadInfoDtoList = new ArrayList<>();

        for (Bread bread : breadList) {
            BreadInfoDto breadInfoDto = BreadInfoDto.builder()
                    .breadId(bread.getId())
                    .breadName(bread.getBreadName())
                    .storeName(bread.getStore().getStoreName())
                    .tingTime(bread.getTingTime()).build();

            breadInfoDtoList.add(breadInfoDto);
        }

        return breadInfoDtoList;
    }

    // 빵 상세 정보 불러오기
    public BreadDetailDto getBreadDetail(Long breadId) {
        Bread bread = breadRepository.findById(breadId)
                .orElseThrow(() -> new EntityNotFoundException("해당 빵이 존재하지 않습니다. id = " + breadId));

        return BreadDetailDto.builder()
                .breadId(bread.getId())
                .breadName(bread.getBreadName())
                .price(bread.getPrice())
                .tingTime(bread.getTingTime())
                .stock(bread.getStock())
                .tingStatus(bread.getTingStatus())
                .storeName(bread.getStore().getStoreName())
                .location(bread.getStore().getLocation()).build();
    }


    /* 빵 등록, 수정, 삭제*/
    public void saveBread(BreadFormDto requestDto, List<MultipartFile> imageFileList) throws Exception {

        Store store = storeRepository.findById(requestDto.getStoreId())
                .orElseThrow(() -> new EntityNotFoundException("해당 스토어가 존재하지 않습니다. id = " + requestDto.getStoreId()));

        Bread bread = requestDto.toEntity(store);
        breadRepository.save(bread);

        // 이미지 등록
        for (int i = 0; i < imageFileList.size(); i++) {
            Image image = new Image();
            image.setBread(bread);

            if(i == 0)
                image.setRepImgYn('Y');     // 첫번째 사진 -> 대표 이미지
            else
                image.setRepImgYn('N');     // 나머지 사진

            imageService.saveImage(image, imageFileList.get(i));
        }
    }

    public void deleteBread(Long breadId) {

        Bread bread = breadRepository.findById(breadId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 빵이 존재하지 않습니다. id = " + breadId));

        // 빵 삭제 시, 빵 이미지 또한 삭제
        List<Image> imageList = imageRepository.findAllByBreadId(bread.getId());

        for (Image image : imageList) {
            imageRepository.delete(image);
        }

        breadRepository.delete(bread);

    }

    public void updateBread(Long breadId, BreadFormDto requestDto, List<MultipartFile> imageFileList) throws Exception {

        // 상품 정보 수정
        Bread bread = breadRepository.findById(breadId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 빵이 존재하지 않습니다. id = " + breadId));

        bread.update(requestDto);
        breadRepository.save(bread);

        // 이미지 등록
        int num = 0;
        for (int i = 0; i < imageFileList.size(); i++) {
            if (num < requestDto.getImageIds().size()) {
                num ++;
                imageService.updateImage(bread, requestDto.getImageIds().get(i), imageFileList.get(i));
            } else {
                imageService.updateImage(bread, 0L, imageFileList.get(i));
            }
        }

        // 남은 등록된 이미지 삭제하기
        for (int i = num; i < requestDto.getImageIds().size(); i++) {
            imageService.deleteImage(requestDto.getImageIds().get(i));
        }
    }

}
