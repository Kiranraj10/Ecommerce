package com.ecommerce.backend.ecommerce_practice.model.DAO;

import com.ecommerce.backend.ecommerce_practice.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerifictionMailDAO extends JpaRepository<VerificationToken, Long> {
}
