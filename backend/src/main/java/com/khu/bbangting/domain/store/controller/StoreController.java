package com.khu.bbangting.domain.store.controller;

import com.khu.bbangting.config.jwt.SecurityUtils;
import com.khu.bbangting.domain.bread.dto.BreadInfoDto;
import com.khu.bbangting.domain.follow.model.Follow;
import com.khu.bbangting.domain.follow.repository.FollowRepository;
import com.khu.bbangting.domain.store.dto.StoreInfoDto;
import com.khu.bbangting.domain.store.service.MyStoreService;
import com.khu.bbangting.domain.store.service.StoreService;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;
    @Autowired
    private MyStoreService myStoreService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FollowRepository followRepository;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> storePage() {
        Map<String, Object> result = new HashMap<>();

        try {
            List<StoreInfoDto> storeInfoList = storeService.getStoreList();
            result.put("storeInfoList", storeInfoList);

            List<StoreInfoDto> storeRankingList = storeService.getTopRank();
            result.put("storeRankingList", storeRankingList);
        } catch (Exception e) {
            result.put("errorMessage", HttpStatus.EXPECTATION_FAILED);
        }

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<Map<String, Object>> storeDetailPage(@PathVariable Long storeId) {
        Map<String, Object> result = new HashMap<>();

        try {
            StoreInfoDto storeInfoDto = storeService.getStoreInfo(storeId);
            result.put("storeInfo", storeInfoDto);

            List<BreadInfoDto> breadList = myStoreService.getBreadList(storeId);
            result.put("breadList", breadList);
        } catch (Exception e) {
            result.put("errorMessage", HttpStatus.EXPECTATION_FAILED);
        }

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchStore(@RequestParam("storeName") String storeName) {
        List<StoreInfoDto> searchResult = storeService.searchBy(storeName);

        if (searchResult.size() == 0) {
            return ResponseEntity.ok().body("검색 결과 없음");
        } else {
            return ResponseEntity.ok().body(searchResult);
        }

    }

    @GetMapping("/{storeId}/{userId}")
    public ResponseEntity<String> checkFollow(@PathVariable Long storeId, @PathVariable Long userId) {
        Optional<Follow> follow = followRepository.findByStoreIdAndUserId(storeId, userId);

        if (follow.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("언팔로우 상태입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.FOUND).body("팔로우 상태입니다.");
        }

    }

}
