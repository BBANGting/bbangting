package com.khu.bbangting.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "breads")
@Getter
public class Bread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "breadId")
    private Long id;

    @Column(nullable = false, length = 20)
    private String breadName;

    private String breadImage;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private Timestamp tingTime;

    @Column(nullable = false)
    private int maxTingNum;

    @Column
    private int stock;

    @ColumnDefault("'Y'")
    private char tingStatus;    // 'Y', 'N'

    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;

}
