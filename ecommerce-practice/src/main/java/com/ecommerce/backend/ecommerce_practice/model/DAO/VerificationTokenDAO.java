package com.ecommerce.backend.ecommerce_practice.model.DAO;

import com.ecommerce.backend.ecommerce_practice.model.LocalUser;
import com.ecommerce.backend.ecommerce_practice.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenDAO extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);

    long deleteByLocalUser(LocalUser localUser);
}
