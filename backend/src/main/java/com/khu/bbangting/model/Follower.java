package com.khu.bbangting.model;

import jakarta.persistence.*;

@Entity
public class Follower {

    @Id
    @Column(name = "followerId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;
}
