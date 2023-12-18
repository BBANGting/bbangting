package com.khu.bbangting.domain.user.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.*;

@NoArgsConstructor
@Entity
@Table(name="users")
@Getter @Setter
@DynamicInsert
public class User {

    @Id
    @Column(name = "userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 100)
    private String username;

    //닉네임 추가
    @Column(nullable = false, length = 10)
    private String nickname;

    @ColumnDefault("0")
    private int banCount;

    @CreationTimestamp
    private Timestamp createdDate;

    private String refreshToken;

    @ColumnDefault("'USER'")
    @Enumerated(EnumType.STRING)
    private Role role;               // USER, ADMIN

    @ColumnDefault("'GENERAL'")
    @Enumerated(EnumType.STRING)
    private Type type;               // GENERAL, RESTRICTED

    @Builder
    public User(String email, String password, String username, String nickname){
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
    }

    public User updatePassword(String newPassword) {
        this.password = newPassword;
        return this;
    }

    public User updateNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public void updateRefreshToken(String newToken) {
        this.refreshToken = newToken;
    }

    public boolean isBanUser(User user) {
        return this.banCount == 5;
    }

}
