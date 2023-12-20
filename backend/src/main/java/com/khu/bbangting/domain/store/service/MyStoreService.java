package com.khu.bbangting.domain.store.service;

import com.khu.bbangting.domain.bread.dto.BreadInfoDto;
import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.bread.repository.BreadRepository;
import com.khu.bbangting.domain.bread.service.BreadOperationService;
import com.khu.bbangting.domain.image.model.Image;
import com.khu.bbangting.domain.image.model.StoreImage;
import com.khu.bbangting.domain.image.repository.ImageRepository;
import com.khu.bbangting.domain.image.repository.StoreImageRepository;
import com.khu.bbangting.domain.store.model.Store;
import com.khu.bbangting.domain.store.repository.StoreRepository;
import com.khu.bbangting.domain.store.dto.MyStoreInfoDto;
import com.khu.bbangting.domain.store.dto.StoreFormDto;
import com.khu.bbangting.domain.image.dto.StoreImageDto;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.domain.image.service.ImageService;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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
    private final BreadOperationService breadOperationService;
    private final BreadRepository breadRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;
    private final StoreImageRepository storeImageRepository;


    @Transactional(readOnly = true)
    public StoreFormDto getStoreForm(Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Store store = storeRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("해당 user가 생성한 스토어가 존재하지 않습니다. userId = " + user.getId()));

        List<StoreImage> storeImgList = storeImageRepository.findByStoreIdOrderByIdAsc(store.getId());
        List<StoreImageDto> storeImgDtoList = new ArrayList<>();
        List<Long> storeImageIds = new ArrayList<>();

        // 저장된 스토어 이미지 찾아 Dto로 변환
        for (StoreImage storeImage : storeImgList) {
            StoreImageDto storeImageDto = StoreImageDto.of(storeImage);
            storeImgDtoList.add(storeImageDto);
            storeImageIds.add(storeImage.getId());
        }

        // 스토어 Dto 생성
        StoreFormDto storeFormDto = StoreFormDto.builder()
                .userId(store.getUser().getId())
                .storeName(store.getStoreName())
                .description(store.getDescription())
                .location(store.getLocation())
                .followerNum(store.getFollowerNum()).build();

        storeFormDto.setStoreImageDtoList(storeImgDtoList);
        storeFormDto.setStoreImageIds(storeImageIds);        // 수정 시, 이미지에 빠르게 접근하기 위해 id만 따로 담아 저장

        return storeFormDto;    }

    public void saveStore(StoreFormDto requestDto, List<MultipartFile> imageFileList) throws Exception{

        // 예외처리) 유저 존재하지 않을 경우
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다. id = " + requestDto.getUserId()));

        // 예외처리) 스토어 로고 이미지 업로드 하지 않은 경우
        if(imageFileList.get(0).isEmpty())
            throw new IllegalArgumentException("스토어 로고는 필수 입력 값입니다.");

        if(imageFileList.get(1).isEmpty())
            throw new IllegalArgumentException("스토어 대표 이미지는 필수 입력 값입니다.");

        // 스토어 정보 저장
        Store store = requestDto.toEntity(user);
        storeRepository.save(store);

        // 이미지 등록
        for (int i = 0; i < imageFileList.size(); i++) {
            StoreImage storeImage = new StoreImage();
            storeImage.setStore(store);

            if(i == 0)
                storeImage.setLogoImgYn('Y');     // 첫번째 사진 -> 로고 이미지
            else
                storeImage.setLogoImgYn('N');     // 두번째 사진 -> 빵집 대표 이미지, 나머지 사진

            imageService.saveStoreImage(storeImage, imageFileList.get(i));
        }

    }

    public void deleteStore(Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Store store = storeRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("해당 user가 생성한 스토어가 존재하지 않습니다. userId = " + user.getId()));

        // 스토어 삭제 시, 등록된 빵 또한 삭제
        List<Bread> breadList = breadRepository.findAllByStoreId(store.getId());
        for (Bread bread : breadList) {
            breadOperationService.deleteBread(bread.getId());
        }

        // 스토어 삭제 시, 스토어 이미지 또한 삭제
        List<StoreImage> storeImageList = storeImageRepository.findAllByStoreId(store.getId());
        for (StoreImage storeImage : storeImageList) {
            storeImageRepository.delete(storeImage);
        }

        storeRepository.delete(store);

    }


    public void updateStore(Authentication authentication, StoreFormDto requestDto, List<MultipartFile> imageFileList) throws Exception {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 예외처리) 스토어가 존재하지 않을 경우
        Store store = storeRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("해당 user가 생성한 스토어가 존재하지 않습니다. userId = " + user.getId()));

        // 예외처리) 스토어 로고 이미지 업로드 하지 않은 경우
        if(imageFileList.get(0).isEmpty())
            throw new IllegalArgumentException("스토어 로고는 필수 입력 값입니다.");

        // 스토어 정보 업데이트
        store.update(requestDto);
        storeRepository.save(store);

        // 이미지 재등록
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
    public MyStoreInfoDto getMyStoreInfo(Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Store store = storeRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("해당 user가 생성한 스토어가 존재하지 않습니다. userId = " + user.getId()));

        StoreImage storeImage = storeImageRepository.findByStoreIdAndLogoImgYn(store.getId(), 'Y');

        return MyStoreInfoDto.builder()
                .storeId(store.getId())
                .storeName(store.getStoreName())
                .imgUrl(storeImage.getImageUrl())
                .followerNum(store.getFollowerNum()).build();
    }

    @Transactional(readOnly = true)
    public List<BreadInfoDto> getBreadList(Long storeId) {
        List<Bread> breadList = breadRepository.findAllByStoreId(storeId);

        List<BreadInfoDto> breadInfoList = new ArrayList<>();
        for (Bread bread : breadList) {
            Image image = imageRepository.findByBreadIdAndRepImgYn(bread.getId(), 'Y');

            BreadInfoDto breadInfoDto = BreadInfoDto.builder()
                    .breadId(bread.getId())
                    .breadName(bread.getBreadName())
                    .imgUrl(image.getImageUrl())
                    .tingStatus(bread.getTingStatus())
                    .tingDateTime(bread.getTingDateTime()).build();

            breadInfoList.add(breadInfoDto);
        }

        return breadInfoList;
    }

    @Transactional(readOnly = true)
    public List<BreadInfoDto> getTodayTingList(Long storeId) {

        List<Bread> breadList = breadRepository.findAllByStoreId(storeId);

        // 현재 오픈되어 있는 빵팅 목록 호출
        List<Bread> todayTingList = new ArrayList<>();
        for (Bread bread : breadList) {
            if (bread.getTingStatus() == 'O') {
                todayTingList.add(bread);
            }
        }

        List<BreadInfoDto> breadInfoList = new ArrayList<>();
        for (Bread bread : todayTingList) {
            Image image = imageRepository.findByBreadIdAndRepImgYn(bread.getId(), 'Y');

            BreadInfoDto breadInfoDto = BreadInfoDto.builder()
                    .breadId(bread.getId())
                    .breadName(bread.getBreadName())
                    .imgUrl(image.getImageUrl())
                    .tingDateTime(bread.getTingDateTime())
                    .maxTingNum(bread.getMaxTingNum())
                    .stock(bread.getStock()).build();

            breadInfoList.add(breadInfoDto);
        }

        return breadInfoList;
    }
}