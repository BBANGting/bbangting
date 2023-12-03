package com.khu.bbangting.domain.image.model;

import com.khu.bbangting.domain.bread.model.Bread;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "images")
@Getter @Setter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imageId")
    private Long id;

    private String imageName;            // 이미지 파일명
    private String oriImageName;         // 원본 이미지 파일명
    private String imageUrl;             // 이미지 조회 경로
    private char repImgYn;             // 대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)   // 빵 정보가 필요할 경우, 데이터 조회
    @JoinColumn(name = "breadId")
    private Bread bread;

    public void updateImage(String oriImageName, String imageName, String imageUrl) {
        this.oriImageName = oriImageName;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

}
