package com.khu.bbangting.domain.store.service;
import com.khu.bbangting.domain.bread.dto.BreadInfoDto;
import com.khu.bbangting.domain.follow.dto.FollowDto;
import com.khu.bbangting.domain.follow.model.Follow;
import com.khu.bbangting.domain.follow.repository.FollowRepository;
import com.khu.bbangting.domain.store.dto.StoreFormDto;
import com.khu.bbangting.domain.store.dto.StoreInfoDto;
import com.khu.bbangting.domain.bread.repository.BreadRepository;
import com.khu.bbangting.domain.store.repository.StoreRepository;
import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.store.model.Store;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.repository.UserRepository;
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
    private final BreadRepository breadRepository;

    // 스토어 목록
    public List<StoreInfoDto> getStoreList() {
        List<Store> storeList = storeRepository.findAll();

        List<StoreInfoDto> storeInfoDtoList = new ArrayList<>();

        for (Store store : storeList) {
            StoreInfoDto storeInfoDto = StoreInfoDto.builder()
                    .storeId(store.getId())
                    .storeName(store.getStoreName())
                    .rating(store.getRating()).build();

            storeInfoDtoList.add(storeInfoDto);
        }

        Collections.sort(storeInfoDtoList, Comparator.comparing(StoreInfoDto::getStoreName));

        return storeInfoDtoList;
    }

    // 스토어 top 랭킹 목록
    public List<StoreInfoDto> getTopRank() {
        List<Store> storeList = storeRepository.findAll();
        Collections.sort(storeList, Comparator.comparing(Store::getRating).reversed());

        List<StoreInfoDto> storeRankingList = new ArrayList<>();

        for (Store store : storeList) {
            StoreInfoDto storeInfoDto = StoreInfoDto.builder()
                    .storeId(store.getId())
                    .storeName(store.getStoreName())
                    .rating(store.getRating()).build();

            storeRankingList.add(storeInfoDto);

            if(storeRankingList.size() == 4) break;
        }

        return storeRankingList;
    }


    public List<StoreInfoDto> searchBy(String storeName) {
        List<Store> storeList = storeRepository.findAllByStoreName(storeName);

        List<StoreInfoDto> searchResultList = new ArrayList<>();

        for (Store store : storeList) {
            StoreInfoDto storeInfoDto = StoreInfoDto.builder()
                    .storeId(store.getId())
                    .storeName(store.getStoreName())
                    .location(store.getLocation())
                    .followerNum(store.getFollowerNum())
                    .rating(store.getRating()).build();

            searchResultList.add(storeInfoDto);
        }

        return searchResultList;
    }

    public StoreFormDto getStoreInfo(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 스토어가 존재하지 않습니다. id = " + storeId));

        return StoreFormDto.builder()
                .storeName(store.getStoreName())
                .description(store.getDescription())
                .location(store.getLocation())
                .followerNum(store.getFollowerNum())
                .rating(store.getRating()).build();
    }

    public List<BreadInfoDto> getBreadList(Long storeId) {
        List<Bread> breadList = breadRepository.findByStoreId(storeId);

        List<BreadInfoDto> breadDtoList = new ArrayList<>();

        for (Bread bread : breadList) {
            BreadInfoDto breadInfoDto = BreadInfoDto.builder()
                    .breadId(bread.getId())
                    .breadName(bread.getBreadName())
                    .storeName(bread.getStore().getStoreName())
                    .tingDateTime(bread.getTingDateTime())
                    .stock(bread.getStock()).build();

            breadDtoList.add(breadInfoDto);
        }

        return breadDtoList;
    }

}