package com.ecommerce.backend.ecommerce_practice.service;

import com.ecommerce.backend.ecommerce_practice.model.DAO.ProductDAO;
import com.ecommerce.backend.ecommerce_practice.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductService {
    private ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> getProducts() {
        return productDAO.findAll(); }
}
