package com.example.helloworldbatch.service;

import com.example.helloworldbatch.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    public ArrayList<Product> getProducts(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8086/products";
        Product[] productsArray = restTemplate.getForObject(url, Product[].class);
        ArrayList<Product> productArrayList = new ArrayList();
        for(Product p : productsArray){
            productArrayList.add(p);
        }

        return productArrayList;
//        ArrayList<Product> products = restTemplate.getForObject(url, ArrayList.class);
//        return products;



    }

}
