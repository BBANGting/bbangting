package com.khu.bbangting.dto;

import com.khu.bbangting.model.Bread;
import com.khu.bbangting.model.Image;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BreadFormDto {

    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String breadName;

    private String breadImage;

    private String description;

    @NotBlank(message = "가격은 필수 입력 값입니다.")
    private int price;

    @NotNull
    private Timestamp tingTime;

    @NotBlank(message = "최대 빵팅 개수는 필수 입력 값입니다.")
    private int maxTingNum;

    private int stock;

    private char tingStatus;

    private List<ImageDto> imageDtoList = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Bread newBread(){
        return modelMapper.map(this, Bread.class);
    }

    public static BreadFormDto of(Bread bread) {
        return modelMapper.map(bread, BreadFormDto.class);
    }
}
