package com.khu.bbangting.dto.store;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class StoreUpdateFormDto {

    @NotBlank(message = "스토어명은 필수 입력 값입니다.")
    private String storeName;

    private String description;

    @NotBlank(message = "스토어의 오프라인 위치는 필수 입력 값입니다.")
    private String location;

}