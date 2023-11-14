package com.khu.bbangting.controller;

import com.khu.bbangting.dto.StoreInfoDto;
import com.khu.bbangting.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StoreController {

    @Autowired
    private StoreService storeService;


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

}
