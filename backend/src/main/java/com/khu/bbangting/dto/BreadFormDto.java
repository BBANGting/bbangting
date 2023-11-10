package com.khu.bbangting.dto;

import com.khu.bbangting.model.Bread;
import com.khu.bbangting.model.Image;
import com.khu.bbangting.model.Store;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class BreadFormDto {

    private Long storeId;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String breadName;

//    private String breadImage;

    private String description;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    @Min(value = 1)
    private int price;

    @NotBlank
    private String tingTime;

    @NotNull(message = "최대 빵팅 개수는 필수 입력 값입니다.")
    @Min(value = 1)
    private int maxTingNum;

    private char tingStatus;

//    private List<ImageDto> imageDtoList = new ArrayList<>();

    @Builder
    public BreadFormDto(Long storeId, String breadName, String description, int price, String tingTime, int maxTingNum, char tingStatus) {
        this.storeId = storeId;
        this.breadName = breadName;
        this.description = description;
        this.price = price;
        this.tingTime = tingTime;
        this.maxTingNum = maxTingNum;
        this.tingStatus = tingStatus;
    }

    public Bread toEntity(Store store) {
        return Bread.builder()
                .store(store)
                .breadName(breadName)
                .description(description)
                .price(price)
                .tingTime(tingTime)
                .maxTingNum(maxTingNum)
                .stock(maxTingNum).build();
    }

}
