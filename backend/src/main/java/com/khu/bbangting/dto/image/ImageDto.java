package com.khu.bbangting.dto.image;

import com.khu.bbangting.model.Image;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ImageDto{

    private Long id;

    private String imgName;       // 이미지 파일명

    private String imageUrl;      // 이미지 조회 경로

    private static ModelMapper modelMapper = new ModelMapper();

    public static ImageDto of(Image image) {
        return modelMapper.map(image, ImageDto.class);
    }

}