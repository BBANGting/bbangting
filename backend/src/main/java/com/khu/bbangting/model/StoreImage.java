package com.khu.bbangting.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "storeImages")
@Getter @Setter
@ToString
public class StoreImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storeImageId")
    private Long id;

    private String imageName;            // 이미지 파일명
    private String oriImageName;         // 원본 이미지 파일명
    private String imageUrl;             // 이미지 조회 경로

    private char logoImgYn;             // 스토어 로고 여부

    @ManyToOne(fetch = FetchType.LAZY)   // 빵 정보가 필요할 경우, 데이터 조회
    @JoinColumn(name = "storeId")
    private Store store;

    public void updateImage(String oriImageName, String imageName, String imageUrl) {
        this.oriImageName = oriImageName;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

}
