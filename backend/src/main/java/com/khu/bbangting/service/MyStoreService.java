package com.khu.bbangting.service;

import com.khu.bbangting.dto.MyStoreInfoDto;
import com.khu.bbangting.dto.StoreFormDto;
import com.khu.bbangting.dto.StoreUpdateFormDto;
import com.khu.bbangting.model.Bread;
import com.khu.bbangting.model.Store;
import com.khu.bbangting.model.User;
import com.khu.bbangting.repository.BreadRepository;
import com.khu.bbangting.repository.StoreRepository;
import com.khu.bbangting.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j @ToString
public class MyStoreService {

    private final StoreRepository storeRepository;

    private final BreadRepository breadRepository;

    private final UserRepository userRepository;


    public StoreFormDto getStoreForm(Long userId) {

        Store store = storeRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 user가 생성한 마이스토어가 존재하지 않습니다. userId = " + userId));

        return StoreFormDto.builder()
                .userId(store.getId())
                .storeName(store.getStoreName())
                .description(store.getDescription())
                .location(store.getLocation())
                .followerNum(store.getFollowerNum()).build();

    }

    public void saveStore(StoreFormDto requestDto) {

        // 예외처리) 유저 존재하지 않을 경우
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다. id = " + requestDto.getUserId()));

        storeRepository.save(requestDto.toEntity(user));

    }

    public void deleteStore(Long userId) {

        Store store = storeRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 user가 생성한 마이스토어가 존재하지 않습니다. userId = " + userId));

        storeRepository.delete(store);

    }


    public void updateStore(Long userId, StoreUpdateFormDto requestDto) {

        Store store = storeRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 user가 생성한 마이스토어가 존재하지 않습니다. userId = " + userId));

        store.update(requestDto);
        log.info(store.toString());
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
