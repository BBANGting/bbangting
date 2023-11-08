package com.khu.bbangting.dto;

import com.khu.bbangting.model.Store;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class StoreFormDto {

    private Long id;

    @NotBlank(message = "스토어명은 필수 입력 값입니다.")
    private String storeName;

//    private String storeLogo;
//
//    private String storeImage;

    private String description;

    @NotBlank(message = "스토어의 오프라인 위치는 필수 입력 값입니다.")
    private String location;


}
