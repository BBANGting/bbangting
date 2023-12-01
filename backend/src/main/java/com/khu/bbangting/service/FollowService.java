package com.khu.bbangting.service;

import com.khu.bbangting.dto.FollowDto;
import com.khu.bbangting.dto.StoreInfoDto;
import com.khu.bbangting.model.Follow;
import com.khu.bbangting.model.Store;
import com.khu.bbangting.model.User;
import com.khu.bbangting.repository.FollowRepository;
import com.khu.bbangting.repository.StoreRepository;
import com.khu.bbangting.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
@ToString
public class FollowService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;


    // 팔로우 등록/취소 기능
    public String follows(FollowDto followDto) {

        // 예외처리) 유저와 스토어가 존재하지 않을 경우
        Store store = storeRepository.findById(followDto.getStoreId())
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 스토어가 존재하지 않습니다."));

        User user = userRepository.findById(followDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 유저가 존재하지 않습니다."));


        // 유저의 해당 가게 follow 여부 판단
        Optional<Follow> followOptional = followRepository.findByStoreIdAndUserId(store.getId(), user.getId());

        if (followOptional.isPresent()) {
            followRepository.delete(followOptional.get());

            // 팔로우 수 update (감소)
            int num = store.getFollowerNum();
            store.setFollowerNum(--num);

            return "팔로우 취소";
        } else {
            Follow follow = new Follow();
            follow.setStore(store);
            follow.setUser(user);

            followRepository.save(follow);

            // 팔로우 수 update (증가)
            int num = store.getFollowerNum();
            store.setFollowerNum(++num);

            return "팔로우 완료";
        }
    }

    // 팔로잉 목록 호출 기능
    public List<StoreInfoDto> getFollowingList(Long userId) {
        List<Follow> followList = followRepository.findAllByUserId(userId);

        List<StoreInfoDto> followingList = new ArrayList<>();
        for (Follow follow : followList) {
            Store store = storeRepository.findById(follow.getStore().getId())
                    .orElseThrow(() -> new EntityNotFoundException("해당 id의 유저가 존재하지 않습니다."));

            StoreInfoDto storeInfoDto = StoreInfoDto.builder()
                    .storeId(store.getId())
                    .storeName(store.getStoreName()).build();

            followingList.add(storeInfoDto);
        }

        return followingList;
    }
}
