package com.khu.bbangting.service;

import com.khu.bbangting.dto.StoreInfoDto;
import com.khu.bbangting.model.Store;
import com.khu.bbangting.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
@ToString
public class StoreService {

    private final StoreRepository storeRepository;


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


}
