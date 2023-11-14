package com.khu.bbangting.dto.bread;

import com.khu.bbangting.model.Bread;
import com.khu.bbangting.model.Store;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

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
                .tingTime(LocalDateTime.parse(tingTime))
                .maxTingNum(maxTingNum)
                .stock(maxTingNum)
                .tingStatus(tingStatus).build();
    }

    public static BreadFormDto fromBread(Bread bread) {
        return BreadFormDto.builder()
                .storeId(bread.getStore().getId())
                .breadName(bread.getBreadName())
                .description(bread.getDescription())
                .price(bread.getPrice())
                .tingTime(String.valueOf(bread.getTingTime()))
                .maxTingNum(bread.getMaxTingNum())
                .tingStatus(builder().tingStatus)
                .build();
    }

}