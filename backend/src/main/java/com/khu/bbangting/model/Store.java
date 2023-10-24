package com.khu.bbangting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storeId")
    private Long id;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false, length = 20)
    private String storeName;

    private String storeLogo;

    private String storeImage;

    private String introduction;

    @Column(nullable = false, length = 256)
    private String location;

    @ColumnDefault("0")
    private int followerNum;

}
