package com.khu.bbangting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imageId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "breadId")
    private Bread bread;

    private String imageUrl;
}
