//package com.khu.bbangting.domain.image.dto;
//
//import com.khu.bbangting.domain.image.model.Image;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//import org.modelmapper.ModelMapper;
//
//@Getter
//@Setter
//@ToString
//public class ImageDto{
//
//    private Long id;
//    private String imageName;
//    private String oriImageName;
//    private String imageUrl;
//    private String repImgYn;
//
//    private static ModelMapper modelMapper = new ModelMapper();
//
//    public static ImageDto of(Image image) {
//        return modelMapper.map(image, ImageDto.class);
//    }
//
//}