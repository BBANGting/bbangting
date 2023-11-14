package com.khu.bbangting.dto.bread;

import com.khu.bbangting.model.Bread;
import com.khu.bbangting.model.Image;
import com.khu.bbangting.model.Store;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class BreadUpdateFormDto {
    private String breadName;

    private String description;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    @Min(value = 1)
    private int price;

    @NotBlank
    private String tingTime;

    @NotNull(message = "최대 빵팅 개수는 필수 입력 값입니다.")
    @Min(value = 1)
    private int maxTingNum;

    @NotNull
    private char tingStatus;

}