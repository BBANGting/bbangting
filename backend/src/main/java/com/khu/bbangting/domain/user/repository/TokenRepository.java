package com.khu.bbangting.domain.user.repository;

import com.khu.bbangting.domain.user.model.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Tokens, Long> {
    Optional<Tokens> findByToken(String token);

    List<Tokens> findAllValidTokenByEmail(String email);
}
