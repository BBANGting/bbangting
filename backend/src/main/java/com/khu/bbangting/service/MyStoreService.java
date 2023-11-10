package com.khu.bbangting.service;

import com.khu.bbangting.dto.StoreFormDto;
import com.khu.bbangting.model.Store;
import com.khu.bbangting.model.User;
import com.khu.bbangting.repository.StoreRepository;
import com.khu.bbangting.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
        System.out.println("스토어 등록 서비스 호출됨");

        // 예외처리) 유저 존재하지 않을 경우
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다. id = " + requestDto.getUserId()));

        storeRepository.save(requestDto.toEntity(user));

    }

    public void 스토어삭제(Long userId) {

        Store store = storeRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 user가 생성한 마이스토어가 존재하지 않습니다. userId = " + userId));

        storeRepository.delete(store);

    }
}
