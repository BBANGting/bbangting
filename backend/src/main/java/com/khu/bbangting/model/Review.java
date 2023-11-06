package com.khu.bbangting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
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

    //Entity 수정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breadId")
    private Bread bread;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    /* 연관관계 */
    public void setOrders(Bread bread) {
        this.bread = bread;
        bread.getReviews()
                .add(this);
    }

    /* 생성 메서드 */
    public static Review createReview(User user, Bread bread) {
        Review review = new Review();
        review.setBread(bread);
        review.setUser(user);

        return review;
    }

}
