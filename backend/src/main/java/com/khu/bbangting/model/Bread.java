package com.khu.bbangting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Bread {

    @Id
    @Column(name = "breadId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String breadName;

    private String breadImage;

    private String content;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private Timestamp tingTime;

    @Column(nullable = false)
    private int maxTingNum;

    @Column(nullable = true)
    private int stock;

    @Column(nullable = false)
    private char tingStatus;

}
