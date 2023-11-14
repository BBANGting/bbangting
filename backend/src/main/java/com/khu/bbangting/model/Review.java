package com.khu.bbangting.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private int rating;

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

    // 별점 수정
    public void changeRating(int rating) {
        this.rating = rating;
    }

    // 리뷰내용 수정
    public void changeContent(String content) {
        this.content = content;
    }

}
