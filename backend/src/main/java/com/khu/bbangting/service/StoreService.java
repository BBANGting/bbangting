package com.khu.bbangting.service;

import com.khu.bbangting.dto.BreadInfoDto;
import com.khu.bbangting.dto.FollowDto;
import com.khu.bbangting.dto.StoreFormDto;
import com.khu.bbangting.dto.StoreInfoDto;
import com.khu.bbangting.model.*;
import com.khu.bbangting.repository.*;
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
    private final BreadRepository breadRepository;
    private final ImageRepository imageRepository;


    // 스토어 목록
    public List<StoreInfoDto> getStoreList() {
        List<Store> storeList = storeRepository.findAll();

        List<StoreInfoDto> storeInfoList = new ArrayList<>();
        for (Store store : storeList) {
            StoreImage storeImage = storeImageRepository.findByStoreIdAndLogoImgYn(store.getId(), 'Y');

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

    // 스토어 top 랭킹 목록
    public List<StoreInfoDto> getTopRank() {
        List<Store> storeList = storeRepository.findAll();
        Collections.sort(storeList, Comparator.comparing(Store::getRating).reversed());

        List<StoreInfoDto> storeRankingList = new ArrayList<>();
        for (Store store : storeList) {
            StoreImage storeImage = storeImageRepository.findByStoreIdAndLogoImgYn(store.getId(), 'Y');

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

    // 스토어 검색
    public List<StoreInfoDto> searchBy(String storeName) {
        List<Store> storeList = storeRepository.findAllByStoreName(storeName);

        List<StoreInfoDto> searchResultList = new ArrayList<>();
        for (Store store : storeList) {
            StoreImage storeImage = storeImageRepository.findByStoreIdAndLogoImgYn(store.getId(), 'Y');

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

    public StoreInfoDto getStoreInfo(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 스토어가 존재하지 않습니다. id = " + storeId));

        StoreImage logoImage = storeImageRepository.findByStoreIdAndLogoImgYn(store.getId(), 'Y');
        StoreImage storeImage = storeImageRepository.findByStoreIdAndLogoImgYn(store.getId(), 'N');

        return StoreInfoDto.builder()
                .storeName(store.getStoreName())
                .imgUrl(logoImage.getImageUrl())
                .imgUrl2(storeImage.getImageUrl())
                .description(store.getDescription())
                .location(store.getLocation())
                .followerNum(store.getFollowerNum())
                .rating(store.getRating()).build();
    }

    public List<BreadInfoDto> getBreadList(Long storeId) {
        List<Bread> breadList = breadRepository.findByStoreId(storeId);

        List<BreadInfoDto> breadDtoList = new ArrayList<>();
        for (Bread bread : breadList) {
            Image image = imageRepository.findByBreadIdAndRepImgYn(bread.getId(), 'Y');

            BreadInfoDto breadInfoDto = BreadInfoDto.builder()
                    .breadId(bread.getId())
                    .breadName(bread.getBreadName())
                    .imgUrl(image.getImageUrl())
                    .tingTime(bread.getTingTime())
                    .stock(bread.getStock()).build();

            breadDtoList.add(breadInfoDto);
        }

        return breadDtoList;
    }

}
