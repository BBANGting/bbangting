package com.khu.bbangting.domain.store.controller;
import com.khu.bbangting.domain.bread.dto.BreadInfoDto;
import com.khu.bbangting.domain.follow.dto.FollowDto;
import com.khu.bbangting.domain.follow.service.FollowService;
import com.khu.bbangting.domain.store.dto.StoreFormDto;
import com.khu.bbangting.domain.store.dto.StoreInfoDto;
import com.khu.bbangting.domain.store.service.StoreService;
import com.khu.bbangting.domain.user.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private FollowService followService;

    @GetMapping("/store")
    public String storePage(Model model) {
        List<StoreInfoDto> storeInfoDtoList = storeService.getStoreList();
        log.info(storeInfoDtoList.toString());
        List<StoreInfoDto> storeRankingList = storeService.getTopRank();
        log.info(storeRankingList.toString());

        model.addAttribute("storeInfoDtoList", storeInfoDtoList);
        model.addAttribute("storeRankingList", storeRankingList);

        return "store/storePage";
    }

    @GetMapping("/store/{storeId}")
    public String storeDetailPage(Model model, @PathVariable Long storeId) {
        StoreFormDto storeFormDto = storeService.getStoreInfo(storeId);
        log.info(storeFormDto.toString());

        model.addAttribute("storeInfo", storeFormDto);

        List<BreadInfoDto> breadList = storeService.getBreadList(storeId);
        log.info(breadList.toString());

        model.addAttribute("breadList", breadList);

        return "store/storeDetailPage";
    }

    @GetMapping("/store/search")
    public String searchStore(Model model, @RequestParam("storeName") String storeName) {
        List<StoreInfoDto> searchResult = storeService.searchBy(storeName);
        log.info(searchResult.toString());

        if (searchResult.size() == 0) {
            model.addAttribute("searchResultList", "검색 결과 없음");
        } else {
            model.addAttribute("searchResultList", searchResult);
        }

        return "store/searchList";
    }

    // 상품 상세 페이지 - 팔로우 기능
    @PostMapping("/store/follow")
    public ResponseDto<String> followStore(@RequestBody FollowDto followDto) {

        try {
            String message = followService.follows(followDto);
            return new ResponseDto<String>(HttpStatus.OK.value(), message);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto<String>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

}