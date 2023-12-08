package com.khu.bbangting.domain.bread.service;

import com.khu.bbangting.domain.bread.dto.BreadSaleDto;
import com.khu.bbangting.domain.bread.dto.BreadInfoDto;
import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.image.model.Image;
import com.khu.bbangting.domain.bread.repository.BreadRepository;
import com.khu.bbangting.domain.image.repository.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ImageRepository imageRepository;


    @Transactional(readOnly = true)
    // 오늘의 빵팅 목록 불러오기
    public List<BreadInfoDto> getTodayTing() {

        // 판매 종료된 빵팅을 제외한 모든 빵 리스트 호출
        List<Bread> breadList = breadRepository.findAllByTingStatusNot('N');

        // tingDate 가 현재 날짜와 같은 빵만 리스트에 추가
        List<Bread> todayTingList = new ArrayList<>();
        for (Bread bread : breadList) {
            if (bread.getTingDateTime().toLocalDate().isEqual(LocalDateTime.now().toLocalDate())) {
                todayTingList.add(bread);
            }
        }

        List<BreadInfoDto> breadInfoDtoList = new ArrayList<>();

        for (Bread bread : todayTingList) {
            Image image = imageRepository.findByBreadIdAndRepImgYn(bread.getId(), 'Y');

            BreadInfoDto breadInfoDto = BreadInfoDto.builder()
                    .breadId(bread.getId())
                    .breadName(bread.getBreadName())
                    .imgUrl(image.getImageUrl())
                    .storeName(bread.getStore().getStoreName())
                    .tingStatus(bread.getTingStatus())
                    .tingDateTime(bread.getTingDateTime()).build();

            breadInfoDtoList.add(breadInfoDto);
        }

        return breadInfoDtoList;
    }

    @Transactional(readOnly = true)
    // 오픈 예정 빵 목록 불러오기
    public List<BreadInfoDto> getOpenLineUp() {
        List<Bread> comingSoonList = breadRepository.findAllByTingStatusOrderByTingDateTime('C');

        List<BreadInfoDto> breadInfoDtoList = new ArrayList<>();

        for (Bread bread : comingSoonList) {

            // 현재 날짜로부터 일주일 치의 빵팅 리스트만 호출
            // -> 넘어가면 break
            if (bread.getTingDateTime().minusDays(7).toLocalDate().isEqual(LocalDateTime.now().toLocalDate())) {
                break;
            }

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
                .storeName(bread.getStore().getStoreName())
                .imgUrl(image.getImageUrl())
                .price(bread.getPrice())
                .tingDateTime(bread.getTingDateTime())
                .stock(bread.getStock())
                .tingStatus(bread.getTingStatus()).build();
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

    public void reOpenBBangTing() {

        List<Bread> closedBreadList = breadRepository.findAllByTingStatus('N');

        for (Bread bread : closedBreadList) {
            LocalDateTime tingDateTime = bread.getTingDateTime();

            if (LocalDateTime.now().toLocalDate().isEqual(tingDateTime.toLocalDate())
                    || LocalDateTime.now().toLocalDate().isBefore(tingDateTime.toLocalDate())) {
                log.info("종료된 빵팅 재오픈!!!");
                bread.updateTingStatus('C');
            }
        }
    }

    public void openBBangTing() {

        // 오픈 예정 빵팅 리스트 호출
        List<Bread> comingSoonList = breadRepository.findAllByTingStatus('C');
        System.out.println("comingSoonList 에 담긴 bread 개수 = " + comingSoonList.size());

        // tingStatus, 오픈 상태로 변경
        for (Bread bread : comingSoonList) {
            LocalDateTime tingDateTime = bread.getTingDateTime();
            LocalDateTime currentDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

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

        // 2) 판매 완료된 빵 중, 재고가 생긴 빵의 tingStatus, 'O'로 변경
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
