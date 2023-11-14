package com.khu.bbangting.controller;

import com.khu.bbangting.dto.StoreInfoDto;
import com.khu.bbangting.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

        model.addAttribute(storeInfoDtoList);
        model.addAttribute(storeRankingList);

        return "/storePage";
    }


}
