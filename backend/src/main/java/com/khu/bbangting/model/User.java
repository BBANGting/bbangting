package com.khu.bbangting.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
@Getter
@Setter
@Builder
public class User{

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
    @Column(length = 10)
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
    public User(String email, String password, String username, String auth){
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public void updateUser(String nickname, String password) {
        this.username = nickname;
        this.password = password;
    }

}