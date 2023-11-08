package com.khu.bbangting.service;

import com.khu.bbangting.dto.StoreFormDto;
import com.khu.bbangting.model.Store;
import com.khu.bbangting.model.User;
import com.khu.bbangting.repository.StoreRepository;
import com.khu.bbangting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MyStoreService {

    private final StoreRepository storeRepository;

    private final UserRepository userRepository;

    public void 스토어등록(StoreFormDto requestDto) {

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. id = " + requestDto.getUserId()));

        storeRepository.save(requestDto.toEntity(user));

    }
}
