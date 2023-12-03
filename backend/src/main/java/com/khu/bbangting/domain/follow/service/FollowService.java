package com.khu.bbangting.domain.follow.service;

import com.khu.bbangting.domain.follow.dto.FollowDto;
import com.khu.bbangting.domain.follow.model.Follow;
import com.khu.bbangting.domain.follow.repository.FollowRepository;
import com.khu.bbangting.domain.store.dto.StoreInfoDto;
import com.khu.bbangting.domain.store.model.Store;
import com.khu.bbangting.domain.store.repository.StoreRepository;
import com.khu.bbangting.domain.user.dto.UserResponseDto;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
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

            // 스토어 팔로워 리스트에서 제거
            store.removeFollower(user);

            return "팔로우 취소";
        } else {
            Follow follow = new Follow();
            follow.setStore(store);
            follow.setUser(user);

            followRepository.save(follow);

            // 팔로우 수 update (증가)
            int num = store.getFollowerNum();
            store.setFollowerNum(++num);

            // 스토어 팔로워 리스트에 추가
            store.addFollower(user);

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

    // 팔로워 목록 호출 기능 -> 알람 보내기 위한 리스트
    public List<UserResponseDto> getFollowerList(Long storeId) {
        List<Follow> followList = followRepository.findAllByStoreId(storeId);

        List<UserResponseDto> followerList = new ArrayList<>();
        for (Follow follow : followList) {
            User user = userRepository.findByEmail(follow.getUser().getEmail())
                    .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

            UserResponseDto userResponseDto = UserResponseDto.builder()
                    .email(user.getEmail())
                    .nickname(user.getNickname()).build();

            followerList.add(userResponseDto);
        }

        return followerList;
    }
}
