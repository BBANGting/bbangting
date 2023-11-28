package com.khu.bbangting.domain.bread.service;

import com.khu.bbangting.domain.bread.dto.BreadInfoDto;
import com.khu.bbangting.domain.bread.dto.BreadDetailDto;
import com.khu.bbangting.domain.bread.dto.BreadFormDto;
import com.khu.bbangting.domain.bread.event.TingUpdatedEvent;
import com.khu.bbangting.domain.bread.event.TingValidator;
import com.khu.bbangting.domain.bread.event.TingCreatedEvent;
import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.image.model.Image;
import com.khu.bbangting.domain.store.model.Store;
import com.khu.bbangting.domain.bread.repository.BreadRepository;
import com.khu.bbangting.domain.store.repository.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
//    private final ImageRepository imageRepository;
//    private final ImageService imageService;

    private final ApplicationEventPublisher eventPublisher; // 이벤트 발생을 위한 빈 주입
    private final TingValidator tingValidator;

    // 등록된 빵 정보 불러오기
    public BreadFormDto getBreadForm(Long breadId) {
//        List<Image> breadImgList = imageRepository.findByBreadIdOrderByIdAsc(breadId);
//        List<ImageDto> breadImgDtoList = new ArrayList<>();
//        List<Long> imageIds = new ArrayList<>();
//
//        for (Image image : breadImgList) {
//            ImageDto imageDto = ImageDto.of(image);
//            breadImgDtoList.add(imageDto);
//            imageIds.add(image.getId());
//        }

        Bread bread = breadRepository.findById(breadId)
                .orElseThrow(() -> new EntityNotFoundException("해당 스토어가 존재하지 않습니다. id = " + breadId));
        BreadFormDto breadFormDto = BreadFormDto.builder()
                .storeId(bread.getStore().getId())
                .breadName(bread.getBreadName())
                .description(bread.getDescription())
                .price(bread.getPrice())
                .maxTingNum(bread.getMaxTingNum())
                .tingStatus(bread.getTingStatus())
                .tingDateTime(bread.getTingDateTime())
                .build();

//        breadFormDto.setImageDtoList(breadImgDtoList);
//        breadFormDto.setImageIds(imageIds);

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
                    .stock(bread.getStock())
                    .tingDateTime(bread.getTingDateTime()).build();

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
                    .tingDateTime(bread.getTingDateTime()).build();

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
                .tingDateTime(bread.getTingDateTime())
                .stock(bread.getStock())
                .tingStatus(bread.getTingStatus())
                .storeName(bread.getStore().getStoreName())
                .location(bread.getStore().getLocation()).build();
    }


    /* 빵 등록, 수정, 삭제*/
    @InitBinder("bread")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(tingValidator);
    }

    public void saveBread(BreadFormDto requestDto) throws Exception {

        Store store = storeRepository.findById(requestDto.getStoreId())
                .orElseThrow(() -> new EntityNotFoundException("해당 스토어가 존재하지 않습니다. id = " + requestDto.getStoreId()));

        Bread bread = requestDto.toEntity(store);
        breadRepository.save(bread);

//        // 이미지 등록
//        for (int i = 0; i < imageFileList.size(); i++) {
//            Image image = new Image();
//            image.setBread(bread);
//
//            if(i == 0)
//                image.setRepImgYn('Y');     // 첫번째 사진 -> 대표 이미지
//            else
//                image.setRepImgYn('N');     // 나머지 사진
//
//            imageService.saveImage(image, imageFileList.get(i));
//        }
    }

    public void deleteBread(Long breadId) {

        Bread bread = breadRepository.findById(breadId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 빵이 존재하지 않습니다. id = " + breadId));

//        // 빵 삭제 시, 빵 이미지 또한 삭제
//        List<Image> imageList = imageRepository.findAllByBreadId(bread.getId());
//
//        for (Image image : imageList) {
//            imageRepository.delete(image);
//        }

        breadRepository.delete(bread);

    }

    public void updateBread(Long breadId, BreadFormDto requestDto, List<MultipartFile> imageFileList) throws Exception {

        // 상품 정보 수정
        Bread bread = breadRepository.findById(breadId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 빵이 존재하지 않습니다. id = " + breadId));

        bread.update(requestDto);
        breadRepository.save(bread);

//        // 이미지 등록
//        for (int i = 0; i < imageFileList.size(); i++) {
//            System.out.println(imageFileList.size());
//            System.out.println(requestDto.getImageIds().get(i));
//            imageService.updateImage(requestDto.getImageIds().get(i), imageFileList.get(i));
//        }
    }

    // 빵팅 등록시
    public void publishTing(Bread bread) {
        System.out.println("publishTing 접근");
        bread.publishTing();
        eventPublisher.publishEvent(new TingCreatedEvent(bread));
    }

    // 빵팅 시작시
    public void startTing(Bread bread) {
        bread.startTing();
        eventPublisher.publishEvent(new TingUpdatedEvent(bread,
                "[" + bread.getStore().getStoreName() + "] " + bread.getBreadName() + " 빵팅이 시작되었습니다!"));
    }

    // 빵팅 재시작시
    public void restartTing(Bread bread) {
        bread.restartTing();
        eventPublisher.publishEvent(new TingUpdatedEvent(bread,
                "[" + bread.getStore().getStoreName() + "] " + bread.getBreadName() + " 예약 취소로 인해 재고가 추가되었습니다!"));
    }

    // 빵팅 종료시
    public void closeTing(Bread bread) {
        bread.closeTing();
    }

}