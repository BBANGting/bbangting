package com.khu.bbangting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.naming.Name;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Bread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "breadId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;

    @Column(nullable = false, length = 20)
    private String breadName;

    private String breadImage;

    @Column(length = 1000)
    private String content;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private Timestamp tingTime;

    @Column(nullable = false)
    private int maxTingNum;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private char tingStatus;    // 'Y', 'N'

}
