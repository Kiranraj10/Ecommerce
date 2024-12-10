package com.ecommerce.backend.ecommerce_practice.model.DAO;

import com.ecommerce.backend.ecommerce_practice.model.LocalUser;
import com.ecommerce.backend.ecommerce_practice.model.WebOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebOrderDAO extends JpaRepository<WebOrder, Long> {

    List<WebOrder> findByLocalUser(LocalUser localUser);
}
