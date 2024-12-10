package com.ecommerce.backend.ecommerce_practice.service;

import com.ecommerce.backend.ecommerce_practice.model.DAO.WebOrderDAO;
import com.ecommerce.backend.ecommerce_practice.model.LocalUser;
import com.ecommerce.backend.ecommerce_practice.model.WebOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    public OrderService(WebOrderDAO webOrderDAO) {
        this.webOrderDAO = webOrderDAO;
    }

    private WebOrderDAO webOrderDAO;

    public List<WebOrder> getOrders(LocalUser localUser) {
        return webOrderDAO.findByLocalUser(localUser);
    }
}
