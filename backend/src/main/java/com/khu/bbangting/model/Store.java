package com.khu.bbangting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "stores")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storeId")
    private Long id;

    @Column(nullable = false, length = 20)
    private String storeName;

    @Column(nullable = false)
    private String storeLogo;

    private String storeImage;

    private String description;

    @Column(nullable = false, length = 256)
    private String location;

    @ColumnDefault("0")
    private int followerNum;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

}
