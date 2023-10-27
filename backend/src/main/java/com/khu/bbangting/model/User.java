package com.khu.bbangting.model;

import com.khu.bbangting.dto.UserFormDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
@Getter @Setter
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

    //엔티티에서 회원 생성 메서드를 만들어 관리하면 -> 코드가 변경되더라도 한 군데만 수정하면 되는 이점이 있음.
    public static User createUser(UserFormDto userFormDto, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setEmail(userFormDto.getEmail());
        String password = passwordEncoder.encode(userFormDto.getPassword());
        user.setPassword(password);
        user.setUsername(userFormDto.getUsername());
        user.setBanCount(0);
        user.setRole(Role.USER);
        user.setType(Type.GENERAL);
        return user;
    }
}