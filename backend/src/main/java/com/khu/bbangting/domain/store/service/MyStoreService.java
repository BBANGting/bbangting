package com.khu.bbangting.domain.store.service;

import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.bread.repository.BreadRepository;
import com.khu.bbangting.domain.image.dto.StoreImageDto;
import com.khu.bbangting.domain.image.repository.ImageRepository;
import com.khu.bbangting.domain.image.service.ImageService;
import com.khu.bbangting.domain.store.dto.MyStoreInfoDto;
import com.khu.bbangting.domain.store.dto.StoreFormDto;
import com.khu.bbangting.domain.store.dto.StoreUpdateFormDto;
import com.khu.bbangting.domain.store.model.Store;
import com.khu.bbangting.domain.image.model.StoreImage;
import com.khu.bbangting.domain.image.repository.StoreImageRepository;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.store.repository.StoreRepository;
import com.khu.bbangting.domain.user.repository.UserRepository;
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
        System.out.println("스토어 등록 서비스 호출됨");

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
        for (int i = 0; i < imageFileList.size(); i++) { // imageFileList = 2
            if (num < requestDto.getStoreImageIds().size()) { // num, i = 2, ids = 3
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

    public MyStoreInfoDto getMyStoreInfo(Long userId) {
        Store store = storeRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 user가 생성한 마이스토어가 존재하지 않습니다. userId = " + userId));

        List<Bread> breadList = breadRepository.findByStoreId(store.getId());

        List<Bread> tingList = breadRepository.findByTingStatusAndAndStoreId('Y', store.getId());

        return MyStoreInfoDto.builder()
                .storeName(store.getStoreName())
                .followerNum(store.getFollowerNum())
                .breadList(breadList)
                .tingList(tingList).build();
    }
}