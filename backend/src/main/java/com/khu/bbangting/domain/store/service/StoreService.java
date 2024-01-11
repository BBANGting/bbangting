package com.khu.bbangting.domain.store.service;

import com.khu.bbangting.domain.image.model.StoreImage;
import com.khu.bbangting.domain.image.repository.StoreImageRepository;
import com.khu.bbangting.domain.store.dto.StoreInfoDto;
import com.khu.bbangting.domain.store.model.Store;
import com.khu.bbangting.domain.store.repository.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
@ToString
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreImageRepository storeImageRepository;


    @Transactional(readOnly = true)
    // 스토어 목록
    public List<StoreInfoDto> getStoreList() {
        List<Store> storeList = storeRepository.findAll();

        List<StoreInfoDto> storeInfoList = new ArrayList<>();
        for (Store store : storeList) {
            StoreImage storeImage = storeImageRepository.findByStoreIdAndLogoImgYn(store.getId(), 'Y')
                    .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 스토어의 로고 이미지를 찾을 수 없습니다."));;

            StoreInfoDto storeInfoDto = StoreInfoDto.builder()
                    .storeId(store.getId())
                    .storeName(store.getStoreName())
                    .imgUrl(storeImage.getImageUrl())
                    .rating(store.getRating()).build();

            storeInfoList.add(storeInfoDto);
        }

        Collections.sort(storeInfoList, Comparator.comparing(StoreInfoDto::getStoreName));

        return storeInfoList;
    }

    @Transactional(readOnly = true)
    // 스토어 top 랭킹 목록
    public List<StoreInfoDto> getTopRank() {
        List<Store> storeList = storeRepository.findAll();
        Collections.sort(storeList, Comparator.comparing(Store::getRating).reversed());

        List<StoreInfoDto> storeRankingList = new ArrayList<>();
        for (Store store : storeList) {
            StoreImage storeImage = storeImageRepository.findByStoreIdAndLogoImgYn(store.getId(), 'Y')
                    .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 스토어의 로고 이미지를 찾을 수 없습니다."));;

            StoreInfoDto storeInfoDto = StoreInfoDto.builder()
                    .storeId(store.getId())
                    .storeName(store.getStoreName())
                    .imgUrl(storeImage.getImageUrl())
                    .rating(store.getRating()).build();

            storeRankingList.add(storeInfoDto);

            if(storeRankingList.size() == 4) break;
        }

        return storeRankingList;
    }

    @Transactional(readOnly = true)
    // 스토어 검색
    public List<StoreInfoDto> searchBy(String storeName) {
        List<Store> storeList = storeRepository.findAllByStoreName(storeName);

        List<StoreInfoDto> searchResultList = new ArrayList<>();
        for (Store store : storeList) {
            StoreImage storeImage = storeImageRepository.findByStoreIdAndLogoImgYn(store.getId(), 'Y')
                    .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 스토어의 로고 이미지를 찾을 수 없습니다."));;

            StoreInfoDto storeInfoDto = StoreInfoDto.builder()
                    .storeId(store.getId())
                    .storeName(store.getStoreName())
                    .imgUrl(storeImage.getImageUrl())
                    .location(store.getLocation())
                    .followerNum(store.getFollowerNum())
                    .rating(store.getRating()).build();

            searchResultList.add(storeInfoDto);
        }

        return searchResultList;
    }

    @Transactional(readOnly = true)
    public StoreInfoDto getStoreInfo(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 스토어가 존재하지 않습니다. id = " + storeId));

        StoreImage logoImage = storeImageRepository.findByStoreIdAndLogoImgYn(store.getId(), 'Y')
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 스토어의 로고 이미지를 찾을 수 없습니다."));;
        StoreImage storeImage = storeImageRepository.findByStoreIdAndLogoImgYn(store.getId(), 'N')
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 스토어의 대표 이미지를 찾을 수 없습니다."));;

        return StoreInfoDto.builder()
                .storeName(store.getStoreName())
                .imgUrl(logoImage.getImageUrl())
                .imgUrl2(storeImage.getImageUrl())
                .description(store.getDescription())
                .location(store.getLocation())
                .followerNum(store.getFollowerNum())
                .rating(store.getRating()).build();
    }

}
