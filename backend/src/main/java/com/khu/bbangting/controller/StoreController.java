package com.khu.bbangting.controller;

import com.khu.bbangting.dto.*;
import com.khu.bbangting.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

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

            List<BreadInfoDto> breadList = storeService.getBreadList(storeId);
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

}
