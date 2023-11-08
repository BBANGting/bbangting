package com.khu.bbangting.dto;

import com.khu.bbangting.model.Store;
import com.khu.bbangting.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@NoArgsConstructor
public class StoreFormDto {

    private Long userId;

    @NotBlank(message = "스토어명은 필수 입력 값입니다.")
    private String storeName;

//    private String storeLogo;
//
//    private String storeImage;

    private String description;

    @NotBlank(message = "스토어의 오프라인 위치는 필수 입력 값입니다.")
    private String location;

    @Builder
    public StoreFormDto(Long userId, String storeName, String description, String location) {
        this.userId = userId;
        this.storeName = storeName;
        this.description = description;
        this.location = location;
    }

    public Store toEntity(User user) {
        return Store.builder()
                .user(user)
                .storeName(storeName)
                .description(description)
                .location(location)
                .build();
    }

}
