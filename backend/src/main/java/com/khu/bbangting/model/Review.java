package com.khu.bbangting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @Column(name = "reviewId")
    @GeneratedValue
    private Long id;

    @Column(nullable = true)
    private int rating;

    @Column(nullable = true)
    private String content;

    @CreationTimestamp
    private Timestamp createdDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="orderId")
    private Order orders;

}
