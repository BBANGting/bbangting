package com.khu.bbangting.service;

import com.khu.bbangting.dto.StoreFormDto;
import com.khu.bbangting.model.Store;
import com.khu.bbangting.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MyStoreService {

    private final StoreRepository storeRepository;

    public void 스토어등록(StoreFormDto storeFormDto, Long userId) throws Exception {

        Store store = storeFormDto.newStore();
        storeRepository.save(store);

    }
}
