package com.khu.bbangting.domain.bread.service;

import com.khu.bbangting.domain.image.dto.ImageDto;
import com.khu.bbangting.domain.bread.dto.BreadSaleDto;
import com.khu.bbangting.domain.bread.dto.BreadFormDto;
import com.khu.bbangting.domain.bread.dto.BreadInfoDto;
import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.image.model.Image;
import com.khu.bbangting.domain.store.model.Store;
import com.khu.bbangting.domain.bread.repository.BreadRepository;
import com.khu.bbangting.domain.image.repository.ImageRepository;
import com.khu.bbangting.domain.store.repository.StoreRepository;
import com.khu.bbangting.domain.image.service.ImageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    public BreadFormDto getBreadForm(Long breadId) {

        List<Image> breadImgList = imageRepository.findByBreadIdOrderByIdAsc(breadId);
        List<ImageDto> breadImgDtoList = new ArrayList<>();
        List<Long> imageIds = new ArrayList<>();

        // 저장된 빵 이미지 찾아 Dto로 변환
        for (Image image : breadImgList) {
            ImageDto imageDto = ImageDto.of(image);
            breadImgDtoList.add(imageDto);
            imageIds.add(image.getId());
        }

        Bread bread = breadRepository.findById(breadId)
                .orElseThrow(() -> new EntityNotFoundException("해당 빵이 존재하지 않습니다. id = " + breadId));

        BreadFormDto breadFormDto = BreadFormDto.builder()
                .storeId(bread.getStore().getId())
                .breadName(bread.getBreadName())
                .description(bread.getDescription())
                .price(bread.getPrice())
                .maxTingNum(bread.getMaxTingNum())
                .tingStatus(bread.getTingStatus())
                .tingDateTime(bread.getTingDateTime())
                .build();

        breadFormDto.setImageDtoList(breadImgDtoList);
        breadFormDto.setImageIds(imageIds);

        return breadFormDto;

    }

    /* 빵 등록, 수정, 삭제*/
    public void saveBread(BreadFormDto requestDto, List<MultipartFile> imageFileList) throws Exception {

        // 예외처리) 스토어 존재하지 않을 경우
        Store store = storeRepository.findById(requestDto.getStoreId())
                .orElseThrow(() -> new EntityNotFoundException("해당 스토어가 존재하지 않습니다. id = " + requestDto.getStoreId()));

        // 예외처리) 빵 대표 이미지 업로드 하지 않은 경우
        if(imageFileList.get(0).isEmpty()){
            throw new IllegalArgumentException("대표 이미지는 필수 입력 값입니다.");
        }

        // 빵 정보 저장
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
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 빵이 존재하지 않습니다. id = " + breadId));

        // 빵 삭제 시, 빵 이미지 또한 삭제
        List<Image> imageList = imageRepository.findAllByBreadId(bread.getId());
        for (Image image : imageList) {
            imageRepository.delete(image);
        }

        breadRepository.delete(bread);

    }

    public void updateBread(Long breadId, BreadFormDto requestDto, List<MultipartFile> imageFileList) throws Exception {

        // 예외처리) 빵 존재하지 않을 경우
        Bread bread = breadRepository.findById(breadId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 빵이 존재하지 않습니다. id = " + breadId));

        // 예외처리) 빵 대표 이미지 업로드 하지 않은 경우
        if(imageFileList.get(0).isEmpty())
            throw new IllegalArgumentException("대표 이미지는 필수 입력 값입니다.");

        // 빵 정보 업데이트
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

    @Transactional(readOnly = true)
    // 오늘의 빵팅 목록 불러오기
    public List<BreadInfoDto> getTodayTing() {
        List<Bread> breadList = breadRepository.findByTingStatusOrderByStore('Y');

        List<BreadInfoDto> breadInfoDtoList = new ArrayList<>();

        for (Bread bread : breadList) {
            Image image = imageRepository.findByBreadIdAndRepImgYn(bread.getId(), 'Y');

            BreadInfoDto breadInfoDto = BreadInfoDto.builder()
                    .breadId(bread.getId())
                    .breadName(bread.getBreadName())
                    .imgUrl(image.getImageUrl())
                    .storeName(bread.getStore().getStoreName())
                    .stock(bread.getStock())
                    .tingDateTime(bread.getTingDateTime()).build();

            breadInfoDtoList.add(breadInfoDto);
        }

        return breadInfoDtoList;
    }

    @Transactional(readOnly = true)
    // 오픈 예정 빵 목록 불러오기
    public List<BreadInfoDto> getOpenLineUp() {
        List<Bread> breadList = breadRepository.findAll();

        List<BreadInfoDto> breadInfoDtoList = new ArrayList<>();

        for (Bread bread : breadList) {
            Image image = imageRepository.findByBreadIdAndRepImgYn(bread.getId(), 'Y');

            BreadInfoDto breadInfoDto = BreadInfoDto.builder()
                    .breadId(bread.getId())
                    .breadName(bread.getBreadName())
                    .imgUrl(image.getImageUrl())
                    .storeName(bread.getStore().getStoreName())
                    .tingDateTime(bread.getTingDateTime()).build();

            breadInfoDtoList.add(breadInfoDto);
        }

        return breadInfoDtoList;
    }

    @Transactional(readOnly = true)
    // 빵 상세 정보 불러오기
    public BreadSaleDto getBreadSaleInfo(Long breadId) {
        Bread bread = breadRepository.findById(breadId)
                .orElseThrow(() -> new EntityNotFoundException("해당 빵이 존재하지 않습니다. id = " + breadId));

        Image image = imageRepository.findByBreadIdAndRepImgYn(bread.getId(), 'Y');

        return BreadSaleDto.builder()
                .breadId(bread.getId())
                .breadName(bread.getBreadName())
                .imgUrl(image.getImageUrl())
                .price(bread.getPrice())
                .stock(bread.getStock())
                .tingStatus(bread.getTingStatus())
                .storeName(bread.getStore().getStoreName())
                .storeName(bread.getStore().getStoreName()).build();
    }

    @Transactional(readOnly = true)
    public List<String> getInfo(Long breadId) {

        Bread bread = breadRepository.findById(breadId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 빵이 존재하지 않습니다. id = " + breadId));

        List<Image> imageList = imageRepository.findAllByBreadIdAndRepImgYn(bread.getId(), 'N');

        List<String> infoList = new ArrayList<>();

        // 스토어 오프라인 위치
        infoList.add(bread.getStore().getLocation());
        // 빵 설명
        infoList.add(bread.getDescription());
        // 빵 이미지 삽입
        for (Image image : imageList) {
            infoList.add(image.getImageUrl());
        }

        return infoList;
    }

    public void closeBBangTing() {

        // 현재 오픈되어 있거나 판매 완료된 빵팅 리스트 호출
        List<Bread> breadList = breadRepository.findAllByTingStatus('O');
        breadList.addAll(breadRepository.findAllByTingStatus('E'));
        log.info(breadList.toString());

        // tingStatus, 판매 종료 상태로 변경
        for (Bread bread : breadList) {
            bread.updateTingStatus('N');
        }
    }

    public void openBBangTing() {

        // 오픈 예정 빵팅 리스트 호출
        List<Bread> comingSoonList = breadRepository.findAllByTingStatus('C');
        System.out.println("comingSoonList.toString() = " + comingSoonList.toString());

        // tingStatus, 오픈 상태로 변경
        for (Bread bread : comingSoonList) {
            LocalDateTime tingDateTime = bread.getTingDateTime();

            LocalDateTime currentDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            log.info(currentDateTime.toString());
            if (currentDateTime.toLocalDate().equals(tingDateTime.toLocalDate())) {
                if (currentDateTime.toLocalTime().equals(tingDateTime.toLocalTime())) {
                    log.info("**** 새로운 빵팅 오픈 완료 *****");
                    bread.updateTingStatus('O');
                }
            }
        }
    }

    public void checkBreadStock() {

        // 1) 재고가 0인 빵의 tingStatus, 'E'로 변경
        List<Bread> openBreadList = breadRepository.findAllByTingStatus('O');

        if (!openBreadList.isEmpty()) {
            for (Bread bread : openBreadList) {
                if (bread.getStock() == 0) {
                    log.info("빵팅 재고 없음 -> 판매 완료 상태로 변경");
                    bread.updateTingStatus('E');
                }
            }
        }

        // 2) 판매 완료된 빵 중, 재고가 생긴 빵의 tingStatus, 'E'로 변경
        List<Bread> endBreadList = breadRepository.findAllByTingStatus('E');

        if (!endBreadList.isEmpty()) {
            for (Bread bread : endBreadList) {
                if (bread.getStock() != 0) {
                    log.info("빵팅 취소표 발생 -> 판매 오픈 상태로 변경");
                    bread.updateTingStatus('O');
                }
            }
        }
    }

}
