package com.ecommerce.backend.ecommerce_practice;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
public class Test {
    public String sayHello() {
        return "hello";
    }
}
