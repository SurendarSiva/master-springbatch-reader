package com.springbatch.testing.controller;

import com.springbatch.testing.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @GetMapping("/products")
    public List<Product> getProducts(){
        List<Product> products = new ArrayList<>();
        products.add(new Product(1,"Dell","Dell laptop", BigDecimal.valueOf(345.12),12));
        products.add(new Product(2,"Apple","Apple Macbook", BigDecimal.valueOf(456.12),14));
        products.add(new Product(3,"Dell","Dell laptop", BigDecimal.valueOf(345.12),12));
        products.add(new Product(4,"Apple","Apple Macbook", BigDecimal.valueOf(456.12),14));
        return products;
    }


}
