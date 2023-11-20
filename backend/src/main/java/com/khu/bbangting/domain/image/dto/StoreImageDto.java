package com.khu.bbangting.domain.image.dto;

import com.khu.bbangting.domain.image.model.StoreImage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@ToString
public class StoreImageDto {

    private Long id;
    private String imageName;
    private String oriImageName;
    private String imageUrl;
    private String logoImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static StoreImageDto of(StoreImage storeImage) {
        return modelMapper.map(storeImage, StoreImageDto.class);
    }

}