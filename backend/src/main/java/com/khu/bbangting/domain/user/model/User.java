package com.khu.bbangting.domain.user.model;

import com.khu.bbangting.domain.follow.model.Follow;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
@Getter @Setter @Builder
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


    // id가 동일하면 같은 객체로 판별
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public boolean isBanUser(User user) {
        return this.banCount == 5;
    }

    public boolean validatePassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.password);
    }
}