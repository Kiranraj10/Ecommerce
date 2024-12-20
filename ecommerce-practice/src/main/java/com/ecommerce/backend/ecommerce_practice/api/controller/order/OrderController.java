package com.ecommerce.backend.ecommerce_practice.api.controller.order;

import com.ecommerce.backend.ecommerce_practice.model.LocalUser;
import com.ecommerce.backend.ecommerce_practice.model.WebOrder;
import com.ecommerce.backend.ecommerce_practice.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping
    public List<WebOrder> getOrders(@AuthenticationPrincipal LocalUser localUser) {
        return orderService.getOrders(localUser);
    }
}
