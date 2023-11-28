package com.khu.bbangting.service;

import com.khu.bbangting.dto.BreadInfoDto;
import com.khu.bbangting.dto.MyStoreInfoDto;
import com.khu.bbangting.dto.StoreFormDto;
import com.khu.bbangting.dto.StoreImageDto;
import com.khu.bbangting.model.*;
import com.khu.bbangting.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j @ToString
public class MyStoreService {

    private final StoreRepository storeRepository;
    private final BreadRepository breadRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;
    private final StoreImageRepository storeImageRepository;


    @Transactional(readOnly = true)
    public StoreFormDto getStoreForm(Long userId) {

        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 user가 생성한 마이스토어가 존재하지 않습니다. userId = " + userId));

        List<StoreImage> storeImgList = storeImageRepository.findByStoreIdOrderByIdAsc(store.getId());
        List<StoreImageDto> storeImgDtoList = new ArrayList<>();
        List<Long> storeImageIds = new ArrayList<>();

        for (StoreImage storeImage : storeImgList) {
            StoreImageDto storeImageDto = StoreImageDto.of(storeImage);
            storeImgDtoList.add(storeImageDto);
            storeImageIds.add(storeImage.getId());
        }

        StoreFormDto storeFormDto = StoreFormDto.builder()
                .userId(store.getUser().getId())
                .storeName(store.getStoreName())
                .description(store.getDescription())
                .location(store.getLocation())
                .followerNum(store.getFollowerNum()).build();

        storeFormDto.setStoreImageDtoList(storeImgDtoList);
        storeFormDto.setStoreImageIds(storeImageIds);

        return storeFormDto;
    }

    public void saveStore(StoreFormDto requestDto, List<MultipartFile> imageFileList) throws Exception{

        // 예외처리) 유저 존재하지 않을 경우
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다. id = " + requestDto.getUserId()));

        Store store = requestDto.toEntity(user);
        storeRepository.save(store);

        // 이미지 등록
        for (int i = 0; i < imageFileList.size(); i++) {
            StoreImage storeImage = new StoreImage();
            storeImage.setStore(store);

            if(i == 0)
                storeImage.setLogoImgYn('Y');     // 첫번째 사진 -> 로고 이미지
            else
                storeImage.setLogoImgYn('N');     // 나머지 사진

            imageService.saveStoreImage(storeImage, imageFileList.get(i));
        }

    }

    public void deleteStore(Long userId) {

        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 user가 생성한 마이스토어가 존재하지 않습니다. userId = " + userId));

        List<StoreImage> storeImageList = storeImageRepository.findAllByStoreId(store.getId());

        for (StoreImage storeImage : storeImageList) {
            storeImageRepository.delete(storeImage);
        }

        storeRepository.delete(store);

    }


    public void updateStore(Long userId, StoreFormDto requestDto, List<MultipartFile> imageFileList) throws Exception {

        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 user가 생성한 마이스토어가 존재하지 않습니다. userId = " + userId));

        store.update(requestDto);
        storeRepository.save(store);

        // 이미지 등록
        int num = 0;
        for (int i = 0; i < imageFileList.size(); i++) {
            if (num < requestDto.getStoreImageIds().size()) {
                num ++;
                imageService.updateStoreImage(store, requestDto.getStoreImageIds().get(i), imageFileList.get(i));
            } else {
                imageService.updateStoreImage(store,0L, imageFileList.get(i));
            }
        }

        // 남은 등록된 이미지 삭제하기
        for (int i = num; i < requestDto.getStoreImageIds().size(); i++) {
            imageService.deleteStoreImage(requestDto.getStoreImageIds().get(i));
        }
    }

    @Transactional(readOnly = true)
    public MyStoreInfoDto getMyStoreInfo(Long userId) {

        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 user가 생성한 마이스토어가 존재하지 않습니다. userId = " + userId));

        StoreImage storeImage = storeImageRepository.findByStoreIdAndLogoImgYn(store.getId(), 'Y');

        return MyStoreInfoDto.builder()
                .storeId(store.getId())
                .storeName(store.getStoreName())
                .imgUrl(storeImage.getImageUrl())
                .followerNum(store.getFollowerNum()).build();
    }

    @Transactional(readOnly = true)
    public List<BreadInfoDto> getBreadList(Long storeId) {
        List<Bread> breadList = breadRepository.findByStoreId(storeId);

        List<BreadInfoDto> breadInfoList = new ArrayList<>();
        for (Bread bread : breadList) {
            Image image = imageRepository.findByBreadIdAndRepImgYn(bread.getId(), 'Y');

            BreadInfoDto breadInfoDto = BreadInfoDto.builder()
                    .breadId(bread.getId())
                    .breadName(bread.getBreadName())
                    .imgUrl(image.getImageUrl()).build();

            breadInfoList.add(breadInfoDto);
        }

        return breadInfoList;
    }

    @Transactional(readOnly = true)
    public List<BreadInfoDto> getTodayTingList(Long storeId) {
        List<Bread> tingList = breadRepository.findByTingStatusAndAndStoreId('Y', storeId);

        List<BreadInfoDto> todayTingList = new ArrayList<>();
        for (Bread bread : tingList) {
            Image image = imageRepository.findByBreadIdAndRepImgYn(bread.getId(), 'Y');

            BreadInfoDto breadInfoDto = BreadInfoDto.builder()
                    .breadId(bread.getId())
                    .breadName(bread.getBreadName())
                    .imgUrl(image.getImageUrl())
                    .maxTingNum(bread.getMaxTingNum())
                    .stock(bread.getStock()).build();

            todayTingList.add(breadInfoDto);
        }

        return todayTingList;
    }
}