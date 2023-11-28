package com.khu.bbangting.domain.review.model;

import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.bread.model.Bread;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@ToString(exclude = {"bread", "user"})
@Table(name = "reviews")
public class Review {

    @Id
    @Column(name = "reviewId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = true)
    private double rating;

    @Column(nullable = true)
    private String content;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breadId")
    private Bread bread;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public void changeRating(double rating) {
        this.rating = rating;
    }

    public void changeContent(String content) {
        this.content = content;
    }

}
