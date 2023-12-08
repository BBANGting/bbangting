package com.khu.bbangting.jwt.repository;

import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.jwt.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUser(User user);
    RefreshToken findByKeyValue(String keyValue);
    void deleteAllByUser(User user);
}
