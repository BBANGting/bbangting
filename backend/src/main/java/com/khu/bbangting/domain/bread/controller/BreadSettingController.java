package com.khu.bbangting.domain.bread.controller;

import com.khu.bbangting.domain.bread.repository.BreadRepository;
import com.khu.bbangting.domain.bread.service.BreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class BreadSettingController {

    private final BreadService breadService;
    private final BreadRepository breadRepository;



}
