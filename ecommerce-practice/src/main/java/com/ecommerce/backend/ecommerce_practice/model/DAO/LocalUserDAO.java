package com.ecommerce.backend.ecommerce_practice.model.DAO;

import com.ecommerce.backend.ecommerce_practice.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalUserDAO extends JpaRepository<LocalUser, Long> {

    Optional<LocalUser> findByEmailIgnoreCase(String email);

    Optional<LocalUser> findByUsernameIgnoreCase(String username);
}
