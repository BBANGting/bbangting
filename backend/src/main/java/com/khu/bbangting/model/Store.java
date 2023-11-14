package com.khu.bbangting.model;

import com.khu.bbangting.dto.StoreUpdateFormDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "stores")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storeId")
    private Long id;

    @Column(nullable = false, length = 20)
    private String storeName;

//    @Column(nullable = false)
//    private String storeLogo;

//    private String storeImage;

    private String description;

    @Column(nullable = false, length = 256, unique = true)
    private String location;

    @ColumnDefault("0")
    private int followerNum;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @ColumnDefault("0")
    private double rating;

    @Builder
    private Store(User user, String storeName, String description, String location) {
        this.user = user;
        this.storeName = storeName;
        this.description = description;
        this.location = location;
    }

    public void update(StoreUpdateFormDto requestDto) {
        this.storeName = requestDto.getStoreName();
        this.description = requestDto.getDescription();
        this.location = requestDto.getLocation();
    }

}
