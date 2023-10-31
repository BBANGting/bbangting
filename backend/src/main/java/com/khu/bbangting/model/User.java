package com.khu.bbangting.model;

import com.khu.bbangting.dto.UserFormDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
@Getter @Setter
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

    @Column(nullable = false)
    @ColumnDefault("0")
    private int banCount;

    @CreationTimestamp
    private Timestamp createdDate;

    @Enumerated(EnumType.STRING)
    private Role role;               // USER, ADMIN

    @Column(nullable = false)
    @ColumnDefault("'general'")
    @Enumerated(EnumType.STRING)
    private Type type;               // GENERAL, RESTRICTED

    @Builder
    public User(String email, String password, String username, String auth){
        this.email = email;
        this.password = password;
        this.username = username;
    }

}