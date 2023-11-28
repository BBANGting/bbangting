package com.khu.bbangting.domain.store.model;

import com.khu.bbangting.domain.store.dto.StoreFormDto;
import com.khu.bbangting.domain.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "stores")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storeId")
    private Long id;

    @Column(nullable = false, length = 20)
    private String storeName;

    private String description;

    @Column(nullable = false, length = 256)
    private String location;

    @ColumnDefault("0")
    private int followerNum;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @ColumnDefault("0")
    private double rating;

    @OneToMany
    private Set<User> followerList = new HashSet<>();

    @Builder
    private Store(User user, String storeName, String description, String location) {
        this.user = user;
        this.storeName = storeName;
        this.description = description;
        this.location = location;
    }

    public void update(StoreFormDto requestDto) {
        this.storeName = requestDto.getStoreName();
        this.description = requestDto.getDescription();
        this.location = requestDto.getLocation();
    }

    public void setFollowerNum(int followerNum) {
        this.followerNum = followerNum;
    }

    public void addFollower(User user) {
        this.followerList.add(user);
    }

    public void removeFollower(User user) {
        this.followerList.remove(user);
    }

    public boolean isManagedBy(User user) {
        return this.getUser().getId() == user.getId();
    }
}