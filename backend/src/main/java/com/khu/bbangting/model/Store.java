package com.khu.bbangting.model;

import com.khu.bbangting.dto.StoreFormDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "stores")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@DynamicInsert
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storeId")
    private Long id;

    @Column(nullable = false, length = 20)
    private String storeName;

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

    public void update(StoreFormDto requestDto) {
        this.storeName = requestDto.getStoreName();
        this.description = requestDto.getDescription();
        this.location = requestDto.getLocation();
    }

    public void setFollowerNum(int followerNum) {
        this.followerNum = followerNum;
    }
}
