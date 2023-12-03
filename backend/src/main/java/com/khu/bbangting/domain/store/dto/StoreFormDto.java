package com.khu.bbangting.domain.store.dto;

import com.khu.bbangting.domain.image.dto.StoreImageDto;
import com.khu.bbangting.domain.store.model.Store;
import com.khu.bbangting.domain.user.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter @Setter
@NoArgsConstructor
public class StoreFormDto {

    private Long userId;

    @NotBlank(message = "스토어명은 필수 입력 값입니다.")
    private String storeName;

    private String description;

    @NotBlank(message = "스토어의 오프라인 위치는 필수 입력 값입니다.")
    private String location;

    private int followerNum;

    private double rating;

    private List<StoreImageDto> storeImageDtoList = new ArrayList<>();
    private List<Long> storeImageIds = new ArrayList<>();

    @Builder
    public StoreFormDto(Long userId, String storeName, String description, String location, int followerNum, double rating) {
        this.userId = userId;
        this.storeName = storeName;
        this.description = description;
        this.location = location;
        this.followerNum = followerNum;
        this.rating = rating;
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
